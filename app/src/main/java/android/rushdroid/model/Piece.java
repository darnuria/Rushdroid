package android.rushdroid.model;

import android.support.annotation.NonNull;

public class Piece implements IPiece {
  private final int id;
  private final int size;
  private Position p;
  private final Direction d;

  /**
   * @param id   a unique integer.
   * @param size a integer superior to 0.
   * @param dir  a direction from android.rushdroid.Direction enum.
   * @param col  Column of the Piece
   * @param lig  Line of the Piece
   * @see Direction
   */
  public Piece(int id, int size, Direction dir, int col, int lig) {
    this(id, size, dir, new Position(col, lig));
  }

  public Piece(int id, int size, Direction dir, Position p) {
    if (size < 1 || size > 3) {
      throw new IllegalArgumentException("Size must be superior to 0 and inferior to 4.");
    }
    this.id = id;
    this.size = size;
    this.p = p;
    this.d = dir;
  }

  /**
   * Set position and modify the current instance.
   *
   * @param pos A android.rushdroid.Position should not be null.
   * @see Position
   */
  public void setPos(@NonNull Position pos) {
    this.p = pos;
  }

  public Piece with_pos(Position pos) {
    return new Piece(this.id, this.size, this.d, pos);
  }

  public int getId() {
    return this.id;
  }

  public int getSize() {
    return this.size;
  }

  @NonNull
  public  Position getPos() {
    return this.p;
  }

  public
  @NonNull
  Direction getOrientation() {
    return this.d;
  }

  @Override
  public @NonNull String toString() {
    return "android.rushdroid.Piece { "
      + " id: " + this.id
      + ", size: " + this.size
      + ", position: " + this.p
      + ", direction: " + d + " }";
  }
}
