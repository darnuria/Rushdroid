package android.rushdroid.model;

import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
/**
 * Created by 3300602 on 15/02/16.
 */
public class GridTest extends TestCase {

  public void testIsIn() throws Exception {

  }

  public void testIsInX() throws Exception {

  }

  public void testIsInY() throws Exception {

  }

  public void testIsEmpty() throws Exception {

  }

  public void testGet() throws Exception {
    Grid g = new Grid();
    for (int i = 0; i < g.MAX_X; i += 1) {
      int x = ((int) Math.random() % (g.MAX_X + 1));
      int y = ((int) Math.random() % (g.MAX_Y + 1));
      final Position p = new Position(x, y);
      g.set(p, ((int) Math.random() % (18 + 1)));
      assertNotEquals("Value of " + p + " is <" + g.get(p) + ">.", g.isEmpty(p), true);
      g.unset(p);
      assertEquals("Value of " + p + " is <" + g.get(p) + ">.", g.isEmpty(p), true);
    }
  }

  public void testSet() throws Exception {
    Grid g = new Grid();
    final int x = ((int) Math.random() % (g.MAX_X + 1));
    final int y = ((int) Math.random() % (g.MAX_Y + 1));
    final Position p = new Position(x, y);
    final int id = ((int) Math.random() % (18 + 1));
    g.set(p, ((int) Math.random() % (18 + 1)));
    assertEquals("Incoherent id.", (Integer) g.get(p), new Integer(id));
  }

  public void testUnset() throws Exception {

  }

  public void testMove() throws Exception {

  }

  public void testToString() throws Exception {

  }
}