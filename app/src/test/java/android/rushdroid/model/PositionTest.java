package android.rushdroid.model;

import android.rushdroid.model.Position;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class PositionTest {
  @Test
  public void constructAndGetters() {
    Position p = new Position(0, 0);
    assertEquals(0, p.getCol());
    assertEquals(0, p.getLig());
  }

  @Test
  public void testGetCol() {
    Position p = new Position(0, 2);
    assertEquals(0, p.getCol());
  }

  @Test
  public void testGetLig() {
    Position p = new Position(0, 2);
    assertEquals(2, p.getLig());
  }

  @Test
  public void testEquals() {
    Position a = new Position(0, 0);
    Position b = new Position(1, 1);
    Position c = new Position(1, 1);
    Position d = new Position(0, 1);
    Position e = new Position(1, 0);
    // Referencial
    assertEquals(a, a);
    // Structural
    assertEquals(b, c);
    assertNotEquals(a, b);
    assertNotEquals(d, e);
  }

  @Test
  public void testAddCol() {
    Position a = new Position(0, 1);
    Position b = new Position(1, 1);
    assertEquals("Column should be equals.", b, a.addCol(1));
    assertNotEquals("Columns should not be equals.", a.addCol(5000), a.addCol(2000));
    assertNotEquals("Columns should not be equals.", a.addCol(-1), a.addCol(-2));
    assertNotEquals("Columns should not be equals.", a.addCol(1), a.addCol(-2));
  }

  @Test
  public void testAddLig() {
    Position a = new Position(0, 1);
    Position b = new Position(1, 1);
    assertEquals("Lines should be equals.", b, a.addCol(1));
    assertNotEquals("Line should not be equals.", a.addLig(5000), a.addLig(2000));
  assertNotEquals("Lines should not be equals.", a.addLig(-1), a.addLig(-2));
    assertNotEquals("Line should not be equals.", a.addLig(1), a.addLig(-2));
  }

  @Test
  public void testAdd() {
    Position a = new Position(1, 1);
    Position b = new Position(1, 1);
    Position c = new Position(2, 2);
    assertEquals(a.add(b), c);
  }
}

