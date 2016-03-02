package android.rushdroid.model;

public interface IPiece {
  int getId();
  int getSize();
  Direction getOrientation();
  Position getPos();
  @Deprecated
  void setPos(Position pos);
}
