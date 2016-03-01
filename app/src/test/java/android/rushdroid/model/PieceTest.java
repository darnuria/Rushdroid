package android.rushdroid.model;

import android.rushdroid.model.Direction;
import android.rushdroid.model.Piece;
import android.rushdroid.model.Position;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.junit.Test;

public class PieceTest {
  @Test
  public void ConstructAndGetters() {
    Piece p = new Piece(0, 2, Direction.HORIZONTAL, 0, 0);
    assertEquals(0, p.getId());
    assertEquals(2, p.getSize());
    assertEquals(new Position(0, 0), p.getPos());
    assertEquals(Direction.HORIZONTAL, p.getDir());
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void InvalidPiece() throws IllegalArgumentException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Size must be superior to 0 and inferior to 4.");
    Piece p0 = new Piece(0, 0, Direction.HORIZONTAL, 0, 0);
    Piece p1 = new Piece(0, 5, Direction.HORIZONTAL, 0, 0);
  }

  @Test
  public void testGetId() {
    Piece p = new Piece(0, 2, Direction.HORIZONTAL, 0, 0);
    assertEquals("Incoherent result", 0, p.getId());
  }

  @Test
  public void testGetSize() {
    Piece p = new Piece(0, 2, Direction.HORIZONTAL, 0, 0);
    assertEquals("Incoherent result.", 2, p.getSize());
  }

  @Test
  public void testGetPos() {
    Piece p = new Piece(0, 2, Direction.VERTICAL, 2, 2);
    assertEquals("Incoherent result.", new Position(2, 2), p.getPos());
  }

  @Test
  public void testGetDir() {
    Piece p = new Piece(0, 2, Direction.VERTICAL, 2, 2);
    assertEquals("Incoherent result.", Direction.VERTICAL, p.getDir());
  }
}

