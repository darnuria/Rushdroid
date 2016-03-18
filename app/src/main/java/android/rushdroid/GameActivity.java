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
    this.game = ((GameApplication) this.getApplication()).game();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_game);
  }

  public void onClickRedo(View v) {
    //app.game();
  }

  public void onClickUndo(View view) {
  }
}
