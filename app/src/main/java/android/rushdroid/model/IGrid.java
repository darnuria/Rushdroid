package android.rushdroid.model;

public interface IGrid {
  boolean isEmpty(IPosition pos);
  Integer get(IPosition pos);
  void set(IPosition pos, Integer id);
  void unset(IPosition pos);
}
