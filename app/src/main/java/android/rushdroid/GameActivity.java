package android.rushdroid;

import android.app.Activity;
import android.os.Bundle;
import android.rushdroid.model.Model;
import android.rushdroid.model.Piece;
import android.rushdroid.model.Position;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by 3300602 on 10/02/16.
 */
public class GameActivity extends Activity {
  Model game;
  private Stack<Piece> undo;
  private Stack<Piece> redo;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    this.game = ((GameApplication) this.getApplication()).game();
    this.undo = game.getUndo();
    this.redo = game.getRedo();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
  }

  public void onClickRedo(View v) {
    if (!redo.empty()) {
      Piece p = redo.pop();
      undo.push(game.piece(p.getId()));
      game.setPiece(p);
    }
  }

  public void onClickUndo(View view) {
    if (!undo.empty()) {
      Piece p = undo.pop();
      redo.push(game.piece(p.getId()));
      game.setPiece(p);
    }
  }
}
