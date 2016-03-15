package android.rushdroid.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

// Todo: Using a queue for implementing a redo function.
public class Model implements IModel {
  final private Grid grid = new Grid();
  final private List<Piece> pieces;

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

  public @NonNull
  Piece piece(int id) {
    return this.pieces.get(id);
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

  public boolean endOfGame() {
    Integer p =  this.grid.get(new Position(4, 2));
    return p != null && p == 0;
  }

  public boolean moveForward(int id) {
    Piece p = this.pieces.get(id);
    Position pos = p.getPos();
    int size = p.getSize();

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