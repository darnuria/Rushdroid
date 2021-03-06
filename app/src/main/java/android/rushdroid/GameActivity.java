package android.rushdroid;

import android.app.Activity;
import android.os.Bundle;
import android.rushdroid.model.Model;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 3300602 on 10/02/16.
 * Manage the Game activity itself.
 */
public class GameActivity extends Activity {
  Model game;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    this.game = ((GameApplication) this.getApplication()).game();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
    TextView tv = (TextView) findViewById(R.id.textView);
    Integer lvl = ((GameApplication) this.getApplication()).puzzle_number(this.game);
    if (tv != null){
      tv.setText("Niveau " + lvl);
    }
  }

  public void onClickRedo(View v) {
    game.redo();
  }

  public void onClickUndo(View view) {
    game.undo();
  }

  public void onClickClear (View v) {
    game.clear();
  }
}

