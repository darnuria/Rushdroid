package android.rushdroid.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

// Todo: Using a queue for implementing a redo function.
public class Model implements IModel {
  final private Grid grid = new Grid();
  final private List<Piece> pieces;
  private final Deque<Piece> undo = new ArrayDeque<>();
  private final Deque<Piece> redo = new ArrayDeque<>();

  public Model(List<Piece> pieces) {
    this.pieces = pieces;
    this.setAllPieces();
  }

  public final List<Piece> pieces() {
    return Collections.unmodifiableList(this.pieces);
  }

  private void setAllPieces() {
    for (Piece p : this.pieces) {
      Position x = p.getPos();
      int id = p.getId();
      for (int i = 0; i < p.getSize(); i += 1) {
        if (p.getOrientation() == Direction.HORIZONTAL) {
          grid.set(x.addCol(i), id);
        } else {
          grid.set(x.addLig(i), id);
        }
      }
    }
  }

  public void setPiece (Piece p) {
    pieces.set(p.getId(), p);
  }

  public @NonNull
  Piece piece(int id) {
    return this.pieces.get(id);
  }

  public void clear () {
    while (!undo.isEmpty()) {
      undo();
    }
    redo.clear();
  }

  public void undo () {
    if (!undo.isEmpty()) {
      Piece p1 = undo.pop();
      Piece p2 = piece(p1.getId());
      changeGrid(p2, p1);
      redo.push(p2);
      setPiece(p1);
    }
  }

  public void redo () {
    if (!redo.isEmpty()) {
      Piece p1 = redo.pop();
      Piece p2 = piece(p1.getId());
      changeGrid(p2, p1);
      undo.push(p2);
      setPiece(p1);
    }
  }

  private void removePiece (Piece p) {
    Position x = p.getPos();
    for (int i = 0; i < p.getSize(); i += 1) {
      if (p.getOrientation() == Direction.HORIZONTAL) {
        grid.set(x.addCol(i), null);
      } else {
        grid.set(x.addLig(i), null);
      }
    }
  }

  private void putPiece (Piece p) {
    Position x = p.getPos();
    for (int i = 0; i < p.getSize(); i += 1) {
      if (p.getOrientation() == Direction.HORIZONTAL) {
        grid.set(x.addCol(i), p.getId());
      } else {
        grid.set(x.addLig(i), p.getId());
      }
    }
  }

  private void changeGrid (Piece remove, Piece set) {
    removePiece(remove);
    putPiece(set);
  }

  public
  @Nullable
  Integer getIdByPos(@NonNull IPosition pos) {
    return this.grid.get(pos);
  }

  public Direction getOrientation(int id) {
    return this.pieces.get(id).getOrientation();
  }

  public int getLig(int id) {
    return this.pieces.get(id).getPos().getLig();
  }

  public int getCol(int id) {
    return this.pieces.get(id).getPos().getCol();
  }

  // TODO: Using soft-wired end-of-game position.
  public boolean endOfGame() {
    Integer id =  this.grid.get(new Position(5, 2));
    return id != null && id == 0;
  }

  public boolean moveForward(int id) {
    Piece p = this.pieces.get(id);
    Position pos = p.getPos();
    int size = p.getSize();

    this.undo.push(p);
    while (!redo.isEmpty()) { redo.pop(); }

    switch (p.getOrientation()) {
      case HORIZONTAL: {
        int x = pos.getCol() + size;
        Position a = pos.addCol(size);
        if (this.grid.isInX(x) && this.grid.isEmpty(a)) {
          this.grid.move(a, pos);
          this.pieces.set(id, p.with_pos(pos.addCol(1)));
          return true;
        }
        return false;
      } case VERTICAL: {
        int y = pos.getLig() + size;
        Position b = pos.addLig(size);
        if (this.grid.isInY(y) && this.grid.isEmpty(b)) {
          this.grid.move(b, pos);
          this.pieces.set(id, p.with_pos(pos.addLig(1)));
          return true;
        }
        return false;
      }
      default: return false;
    }
  }

  public boolean moveBackward(int id) {
    Piece p = this.pieces.get(id);
    Position pos = p.getPos();
    int offset = p.getSize() - 1;

    this.undo.push(p);
    while (!redo.isEmpty()) { redo.pop(); }

    switch (p.getOrientation()) {
      case HORIZONTAL: {
        int x = pos.getCol() - 1;
        Position a = pos.addCol(-1);
        if (this.grid.isInX(x) && this.grid.isEmpty(a)) {
          this.grid.move(a, pos.addCol(offset));
          this.pieces.set(id, p.with_pos(a));
          return true;
        }
        return false;
      } case VERTICAL: {
        int y = pos.getLig() - 1;
        Position b = pos.addLig(-1);
        if (this.grid.isInY(y) && this.grid.isEmpty(b)) {
          this.grid.move(b, pos.addLig(offset));
          this.pieces.set(id, p.with_pos(b));
          return true;
        }
        return false;
      }
      default: return false;
    }
  }
}