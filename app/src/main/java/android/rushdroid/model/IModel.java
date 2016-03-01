package android.rushdroid.model;

public interface IModel {
  Integer getIdByPos(IPosition pos);
  Direction getOrientation(int id);
  int getLig(int id);
  int getCol(int id);
  boolean endOfGame();
  boolean moveForward(int id);
  boolean moveBackward(int id);
}
