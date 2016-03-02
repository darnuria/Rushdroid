package android.rushdroid;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by 3300602 on 10/02/16.
 * Draw off the UI thread.
 * @see Thread
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
  protected void setRunning(boolean running) {
    this.running = running;
  }

  /**
   * Update Game State. Draw state to the Canvas.
   */
  @Override
  public void run() {
    // update game state
    // render state to the screen
    while (running) {
      Canvas c = holder.lockCanvas();
      try {
        if (c != null) {
          synchronized (holder) {
            // HACK: useless warning from android studio.
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