package android.rushdroid.model;

import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

final public class Position implements IPosition {
  private final int x;
  private final int y;

  /**
   * @param x An integer
   * @param y An integer.
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * @return x.
   */
  public int getCol() {
    return this.x;
  }

  /**
   * @return y.
   */
  public int getLig() {
    return this.y;
  }

  /**
   * Add an Integer d to the private field x. return a new Position.
   * @param d an Integer.
   * @return A new Position.
   */
  @NonNull
  public Position addCol(int d) {
    return new Position(this.x + d, this.y);
  }

  /**
   * Add d to private field y. Return a new Position
   * @param d an Integer.
   * @return A new Position
   */
  @NonNull
  public Position addLig(int d) {
    return new Position(this.x, this.y + d);
  }

  /**
   * Addition between two Positions.
   * @param p an other position should not be null.
   */
  @NonNull
  public Position add(@NonNull Position p) {
    return new Position(this.x + p.x, this.y + p.y);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) { return true; }
    if (o == null || this.getClass() != o.getClass()) { return false; }

    Position p = (Position) o;
    return this.x == p.x && this.y == p.y;
  }

  @Override
  public int hashCode() {
    return 31 * this.x + this.y;
  }

  @Override
  @NonNull
  public String toString() {
    return "{ android.rushdroid.Position col: "
            + this.x + ", lig: "
            + this.y
            + " }";
  }
}
