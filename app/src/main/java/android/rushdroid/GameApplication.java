package android.rushdroid;

import android.app.Application;
import android.content.res.AssetManager;
import android.rushdroid.model.Model;
import android.rushdroid.model.ModelXML;
import android.rushdroid.model.NotFound;
import android.rushdroid.model.Piece;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axel on 2/21/16.
 * Save the state of the game.
 */
public class GameApplication extends Application {
  private Model game;
  private List<Model> games;

  public void selectPuzzle(int i) throws NotFound {
    try {
      game = games.get(i);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFound("Invalid id greater than puzzles number.");
    }
  }

  public int size() { return games.size(); }

  public int puzzle_number(Model m) { return games.indexOf(m); }

  @Override
  public void onCreate() {
    AssetManager am = this.getApplicationContext().getAssets();
    try {
      this.games = new ModelXML().read(am.open("rushpuzzles.xml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.onCreate();
  }

  public Model game() { return game; }
}
