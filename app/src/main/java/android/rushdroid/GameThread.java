
package android.rushdroid;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.lang.ref.WeakReference;

/**
 * Created by 3300602 on 10/02/16.
 */
class GameThread extends Thread {
  final private GameView view;
  final private SurfaceHolder holder;
  private boolean running = false;

  public GameThread(GameView view, SurfaceHolder holder) {
    this.view = view;
    this.holder = holder;
  }

  // flag to hold game state
  public void setRunning(boolean running) {
    this.running = running;
  }

  @Override
  public void run() {
      // update game state
      // render state to the screen
    while (running) {
      Canvas c = holder.lockCanvas();
      try {
        if (c != null) {
          synchronized (holder) {
            view.onDraw(c);
          }
        }
      } finally {
        if (c != null) {
          this.holder.unlockCanvasAndPost(c);
        }
      }
    }
  }
}