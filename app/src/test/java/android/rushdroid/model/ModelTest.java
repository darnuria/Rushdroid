package android.rushdroid.model;

import org.junit.Test;

public class ModelTest{
  @Test
  public void testGetIbBP() {
    Model m = new Model();
    Position p = new Position(1, 2);
    assertEquals("Invalid Id", new Integer(0), m.getIdByPos(p));
  }

  @Test
  public void testGetLig() {
    Model m = new Model();
    assertEquals("Invalid Lig", new Integer(0), m.getLig(1));
  }

  @Test
  public void testGetCol() {
    Model m = new Model();
    assertEquals("Invalid Col", new Integer(0) , m.getCol(1));
  }

  @Test
  public void testEndOfGame() {
    Model m = new Model();
    assertEquals("Should be equals to false.", false, m.endOfGame());
  }

  @Test
  public void testMoveFWD() {
    Model m = new Model();
    final Integer z = new Integer(0);
    final Integer i = new Integer(1);
    final Integer y = new Integer(4);

    //test vertical
    assertEquals("Invalid Lig", z, m.getLig(2));
    m.moveForward(2);
    assertEquals("Error in moveForward vertical: Should be equal to 1.", i, m.getLig(2));

    //test carambolage vertical
    m.moveForward(2);
    assertEquals("Error in moveForward vertical: Should be equal to 1.", i, m.getLig(2));

    //test y a un mur pas bouger
    assertEquals("Invalid Lig", y, m.getLig(5));
    m.moveForward(5);
    assertEquals("Error in moveForward vertical: Should be equal to 4.", y, m.getLig(5));

    //test horizontal
    assertEquals("Invalid Col", z, m.getCol(1));
    m.moveForward(1);
    assertEquals("Error in moveForward horizontal: Should be equal to 1.", i, m.getCol(1));

    //test carambolage horizontal
    m.moveForward(0);
    assertEquals("Error in moveForward horizontal collision with other piece: Should be equal to 1.", i, m.getCol(0));

    //test mur horizontal
    assertEquals("Invalid Col", y, m.getCol(6));
    m.moveForward(6);
    assertEquals("Error in moveForward Horizontal collision with a wall: Should be equal to 4.", y, m.getCol(6));
  }

  @Test
  public void testMoveBWD() {
    Model m = new Model();

    //test vertical
    assertEquals("Invalid Lig", 1, m.getLig(4));
    m.moveBackward(4);
    assertEquals("Error in moveBackward vertical: Should be equal to 0.", 0, m.getLig(4));

    //test carambolage vertical
    m.moveBackward(5);
    assertEquals("Error in moveBackward vertical collision with other piece: Should be equal to 4.", 4, m.getLig(5));

    //test y a un mur pas bouger
    assertEquals("Invalid Lig", 0, m.getLig(2));
    m.moveBackward(2);
    assertEquals("Error in moveBackward vertical. Should be equal to 0.", 0, m.getLig(2));

    //test horizontal
    assertEquals("Invalid Col", 4, m.getCol(6));
    m.moveBackward(6);
    assertEquals("Error in moveBackward horizontal. Should be equal to 3.", 3, m.getCol(6));

    //test carambolage horizontal
    m.moveBackward(0);
    assertEquals("Error in moveBackward horizontal collision with other piece: Should be equal to 1.", 1, m.getCol(0));

    //test mur horizontal
    assertEquals("Invalid Col", 0, m.getCol(1));
    m.moveBackward(1);
    assertEquals("Error in moveBackward horizontal collision with wall: Should be equal to 0.", 0, m.getCol(1));
  }
}
