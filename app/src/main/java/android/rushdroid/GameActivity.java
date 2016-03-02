package android.rushdroid;

import android.app.Activity;
import android.os.Bundle;
import android.rushdroid.model.Model;
import android.rushdroid.model.Position;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 3300602 on 10/02/16.
 */
public class GameActivity extends Activity {
  Model game;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    Model game = ((GameApplication) this.getApplication()).game();
    // Right now this is useless Since the game return a Immutable List of Piece.
    /*
    Map<Position, Integer> m = new HashMap<>();
    for (int l = 0; l < 6; l++) {
      for (int c = 0; c < 6; c++) {
        Position p = new Position(c,l);
        Integer i = game.getIdByPos(p);
        if (i != null) { m.put(p,i); }
      }
    }
    */
    this.game = game;

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
  }

  public void onClickRedo(View v) {
    //app.game();
  }

  public void onClickUndo(View view) {
  }
}
