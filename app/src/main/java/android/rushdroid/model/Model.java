package android.rushdroid.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// Todo: Using a queue for implementing a redo function.
public class Model implements IModel {
  final private Grid grid = new Grid();
  final private List<Piece> pieces = new ArrayList<>();
  final private List<List<Piece>> puzzles = new ArrayList<>();

  public int size() { return puzzles.size(); }

  // TODO: Writting an implementation of Iteratof for nodeList
  public Model(@NonNull InputStream is) {
    Document xml = this.readXMLFile(is);
    System.out.println(xml);
    NodeList puzzles = xml.getElementsByTagName("puzzle");
    for (int i = 0; i < puzzles.getLength(); i++) {
      List<Piece> l = new ArrayList<>();
      Node puzzle = puzzles.item(i);
      if (puzzle.getNodeType() != Node.TEXT_NODE) {
        NodeList pieces = puzzle.getChildNodes();
        for (int j = 0; j < pieces.getLength(); j++) {
          Node p = pieces.item(j);
          if (p.getNodeType() != Node.TEXT_NODE) {
            l.add(this.build_from_attr(p.getAttributes()));
          }
        }
      }
      this.puzzles.add(l);
    }
  }

  public void selectPuzzle(int i) throws NotFound {
    try {
      this.pieces.clear();
      List<Piece> old = this.puzzles.get(i);
      this.pieces.addAll(old);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFound("Invalid id greater than puzzles number.");
    }
    this.setAllPieces();
  }

  public final List<Piece> pieces() {
    return Collections.unmodifiableList(this.pieces);
  }

  private Direction from_string(String s) {
    switch (s) {
      case "H": {
        return Direction.HORIZONTAL;
      }
      case "V": {
        return Direction.VERTICAL;
      }
      default: {
        throw new Error(new Exception("H or V Understand? See DTD!"));
      }
    }
  }

  private Piece build_from_attr(NamedNodeMap attr) {
    Direction dir = from_string(attr.getNamedItem("dir").getTextContent());
    int id = Integer.parseInt(attr.getNamedItem("id").getTextContent());
    int len = Integer.parseInt(attr.getNamedItem("len").getTextContent());
    int col = Integer.parseInt(attr.getNamedItem("col").getTextContent());
    int lig = Integer.parseInt(attr.getNamedItem("lig").getTextContent());
    return (new Piece(id, len, dir, col, lig));
  }

  private void setAllPieces() {
    for (Piece p : this.pieces) {
      Position x = p.getPos();
      int id = p.getId();
      for (int i = 0; i < p.getSize(); i += 1) {
        if (p.getOrientation() == Direction.HORIZONTAL) {
          grid.set(x.addCol(i), id);
        } else {
          grid.set(x.addLig(i), id);
        }
      }
    }
  }

  public
  @Nullable
  Integer getIdByPos(@NonNull IPosition pos) {
    return this.grid.get(pos);
  }

  public Direction getOrientation(int id) {
    return this.pieces.get(id).getOrientation();
  }

  public int getLig(int id) {
    return this.pieces.get(id).getPos().getLig();
  }

  public int getCol(int id) {
    return this.pieces.get(id).getPos().getCol();
  }

  public boolean endOfGame() {
    return this.grid.get(new Position(4, 2)) == 0;
  }


  public boolean moveForward(int id) {
    Piece p = this.pieces.get(id);
    Position pos = p.getPos();
    int size = p.getSize();

    switch (p.getOrientation()) {
      case HORIZONTAL: {
        int x = pos.getCol() + size;
        Position a = pos.addCol(size);
        if (this.grid.isInX(x) && this.grid.isEmpty(a)) {
          this.grid.move(a, pos);
//        p.setPos(pos.addCol(1));
          this.pieces.set(id, p.with_pos(pos.addCol(1)));
          return true;
        }
        return false;
      } case VERTICAL: {
        int y = pos.getLig() + size;
        Position b = pos.addLig(size);
        if (this.grid.isInY(y) && this.grid.isEmpty(b)) {
          this.grid.move(b, pos);
//        p.setPos(pos.addLig(1));
          this.pieces.set(id, p.with_pos(pos.addLig(1)));
          return true;
        }
        return false;
      }
      default: return false;
    }
  }

  public boolean moveBackward(int id) {
    Piece p = this.pieces.get(id);
    Position pos = p.getPos();
    int offset = p.getSize() - 1;

    switch (p.getOrientation()) {
      case HORIZONTAL: {
        int x = pos.getCol() - 1;
        Position a = pos.addCol(-1);
        if (this.grid.isInX(x) && this.grid.isEmpty(a)) {
          this.grid.move(a, pos.addCol(offset));
//        p.setPos(a);
          this.pieces.set(id, p.with_pos(a));
          return true;
        }
        return false;
      } case VERTICAL: {
        int y = pos.getLig() - 1;
        Position b = pos.addLig(-1);
        if (this.grid.isInY(y) && this.grid.isEmpty(b)) {
          this.grid.move(b, pos.addLig(offset));
//        p.setPos(b);
          this.pieces.set(id, p.with_pos(b));
          return true;
        }
        return false;
      }
      default: return false;
    }
  }

  // TODO: Managing error.
  @NonNull
  private Document readXMLFile(@NonNull InputStream is) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      //factory.setValidating(true); // ANDROID FUCK!
      //factory.setNamespaceAware(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(is);
    } catch (@NonNull ParserConfigurationException | IOException | SAXException e) {
      e.printStackTrace();
      throw new IOError(e);
    }
  }
}
/*
  public static void main(String[] argv) {
    final Model m = new Model("./rushpuzzles.xml");
    System.out.println(m.endOfGame());
    while (m.endOfGame() == false) {
      System.out.println(m);
      Scanner keyboard = new Scanner(System.in);
      System.out.println("w: up; a: left; s: down; d: right");
      String s0 = keyboard.nextLine();
      int col = Integer.parseInt(s0);
      s0 = keyboard.nextLine();
      int lig = Integer.parseInt(s0);
      int id = m.getIdByPos(new Position(lig, col));
      System.out.println("id: " + id);
      String s1 = keyboard.nextLine();
      boolean dir = (0 == s1.charAt(0) ? false : true);
      if (dir) {
        m.moveForward(id);
      } else {
        m.moveBackward(id);
      }
    }
  }
  */

