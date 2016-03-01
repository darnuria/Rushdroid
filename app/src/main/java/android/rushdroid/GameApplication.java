package android.rushdroid;

import android.app.Application;
import android.content.res.AssetManager;
import android.rushdroid.model.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by darnuria on 2/21/16.
 */
public class GameApplication extends Application {
  private Model game;

  @Override
  public void onCreate() {
    AssetManager am = this.getApplicationContext().getAssets();
    try {
      InputStream in = am.open("rushpuzzles.xml");
      this.game = new Model(in);
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.onCreate();
  }

  public Model game() { return game;
  }
}
