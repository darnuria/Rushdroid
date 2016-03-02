package android.rushdroid.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Grid implements IGrid {
  final public int MAX_X = 5;
  final public int MAX_Y = 5;
  final private Integer[][] grid = new Integer[MAX_X + 1][MAX_Y + 1];

  public Grid() {
    for (int i = 0; i < 6; i += 1) {
      for (int j = 0; j < 6; j += 1) {
        this.grid[i][j] = null;
      }
    }
  }

  /*
  protected boolean isIn(@NonNull Position pos) {
    int x = pos.getCol();
    int y = pos.getLig();
    return isInX(x) && isInY(y);
  }
  */

  protected boolean isInX(int x) {
    return (x >= 0 && x <= MAX_X);
  }

  protected boolean isInY(int y) {
    return (y >= 0 && y <= MAX_Y);
  }

  public boolean isEmpty(@NonNull IPosition pos) {
    return this.get(pos) == null;
  }

  public
  @Nullable
  Integer get(@NonNull IPosition pos) {
    return this.grid[pos.getCol()][pos.getLig()];
  }

  public void set(@NonNull IPosition pos, @Nullable Integer id) {
    this.grid[pos.getCol()][pos.getLig()] = id;
  }

  public void unset(@NonNull IPosition pos) {
    this.grid[pos.getCol()][pos.getLig()] = null;
  }

  // Not in specification
  public void move(@NonNull IPosition a, @NonNull IPosition b) {
    this.set(a, this.get(b));
    this.unset(b);
  }
}
