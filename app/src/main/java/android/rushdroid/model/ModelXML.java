package android.rushdroid.model;

import android.support.annotation.NonNull;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
// TODO: use it for saving import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public final class ModelXML {
  private List<Piece> construct_puzzle(Node puzzle) {
    List<Piece> l = new ArrayList<>();

    if (puzzle.getNodeType() != Node.TEXT_NODE) {
      NodeList pieces = puzzle.getChildNodes();
      for (int j = 0; j < pieces.getLength(); j++) {
        Node p = pieces.item(j);
        if (p.getNodeType() != Node.TEXT_NODE) {
          l.add(build_from_attr(p.getAttributes()));
        }
      }
    }
    return l;
  }

  private Piece build_from_attr(NamedNodeMap attr) {
    Direction dir = from_string(attr.getNamedItem("dir").getTextContent());
    int id = Integer.parseInt(attr.getNamedItem("id").getTextContent());
    int len = Integer.parseInt(attr.getNamedItem("len").getTextContent());
    int col = Integer.parseInt(attr.getNamedItem("col").getTextContent());
    int lig = Integer.parseInt(attr.getNamedItem("lig").getTextContent());
    return new Piece(id, len, dir, col, lig);
  }

  public List<Model> read(@NonNull InputStream is) {
      Document xml = readXMLFile(is);
      NodeList puzzles = xml.getElementsByTagName("puzzle");
      List<Model> ms = new ArrayList<>();
      for (int i = 0; i < puzzles.getLength(); i++) {
        List<Piece> l = construct_puzzle(puzzles.item(i));
        ms.add(new Model(l));
    }
    return ms;
  }

/* TODO: Implement this for saving.
  public void write(@NonNull OutputStream os) { }
*/

  // TODO: Managing error.
  @NonNull
  private Document readXMLFile(@NonNull InputStream is) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      //factory.setValidating(true); // Not working on Android...
      //factory.setNamespaceAware(false);
      DocumentBuilder builder = factory.newDocumentBuilder();
      return builder.parse(is);
    } catch (@NonNull ParserConfigurationException | IOException | SAXException e) {
      e.printStackTrace();
      throw new IOError(e);
    }
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
}
