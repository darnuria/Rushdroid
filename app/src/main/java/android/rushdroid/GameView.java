package android.rushdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.rushdroid.model.Model;
import android.rushdroid.model.Piece;
import android.rushdroid.model.Position;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOError;
import java.util.List;

/**
 * Created by Corentin-Axel on 08/02/16.
 * Classe responsible of the rendering. Use a GameThread for drawing of the UI Thread.
 * @see GameThread
 */

final public class GameView extends SurfaceView {
  private int X;
  private int Y;
  private final int H = 6;
  private final int V = 6;
  private final int[] xs = new int[H];
  private final int[] ys = new int[V];
  private final Context  context;

  final private void fillArray(int[] a, int b, int c) {
    int d = ;
    a[0] = 1;
    for (int i = 1; i < a.length; i++) {
      a[i] = d * i;
    }
  }

  public GameView(@NonNull Context context, @NonNull AttributeSet attrs) {
    super(context, attrs);
    this.context = context;

    getHolder().addCallback(new SurfaceHolder.Callback() {
      private GameThread th;

      @Override
      public void surfaceCreated(@NonNull SurfaceHolder h) {
        this.th = new GameThread(GameView.this, h);
        X = GameView.this.getWidth();
        Y = GameView.this.getHeight();
        fillArray(xs, X, H);
        fillArray(ys, Y, V);
        this.th.setRunning(true);
        this.th.start();
      }

      @Override
      public void surfaceDestroyed(@NonNull SurfaceHolder h) {
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        this.th.setRunning(false);
        boolean retry = true;
        while (retry) {
          try {
            this.th.join();
            retry = false;
          } catch (InterruptedException e) {
            // try again shutting down the thread
          }
        }
      }

      @Override
      public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
      }
//    this.app = (GameApplication) context.getApplicationContext();
    });
  }
  private void drawGrid(Canvas c) {
    final Paint p = new Paint();
    p.setColor(Color.RED);
    for (int x: xs) {
      c.drawLine(x, 1, x, this.Y, p);
    }
    for (int y: ys) {
      c.drawLine(1, y, this.X, y, p);
    }
    c.drawLine(0, this.Y - 1, this.X - 1, this.Y - 1, p);
    c.drawLine(this.X - 1, 0, this.X - 1, this.X - 1, p);
  }

  /* TODO:
  Dans l'idée cette fonction dois afficher toute les pieces d'un jeu.
  Les Pièces sont exposées comme une collection(liste) immutable.
   */
  private void drawGame(@NonNull Canvas c, List<Piece> gameState) {
    for (Piece p : gameState) {
      int ratioX = this.X / this.H;
      int ratioY = this.Y / this.V;
      int x0 = p.getPos().getCol();
      int y0 = p.getPos().getLig();


      switch (p.getOrientation()) {
        case HORIZONTAL: {
          int y1 = p.getSize() * ratioY;
          int x1 = p.getSize() * ratioX;
        } case VERTICAL: {

        }

      }
    }
  }

  @Override
  public void onDraw(@NonNull Canvas c) {
    c.drawColor(Color.BLACK);
    drawGrid(c);
    drawGame(c, ((GameApplication) context.getApplicationContext()).game().pieces(););
  }

  /**
   * @param x A position on the screen
   * @param y A position on the screen
   * @return A Position representing x and y on our board.
   *
   * Example: if we get x = 50 and y = 100 on a SurfaceView with width = 250 and height = 400
   * And our board have a width of 5 and a height of 5.
   * We will get a Position like this: Position { x: 1; y: 1 }
   * @see Position
   */
  private Position interpolation(int x, int y) {
    return new Position(x * this.H / this.X, y * this.V / this.Y);
  }

  @Override
  public boolean onTouchEvent(@NonNull MotionEvent e) {
    int x = (int) e.getX();
    int y = (int) e.getY();

    switch (e.getAction()) {
      case MotionEvent.ACTION_DOWN: {
        System.out.println(interpolation(x, y));
        // TODO: Write in memory-BMP instead of in the file.
        return true;
      }
      case MotionEvent.ACTION_MOVE: {
        return false;
      }
      case MotionEvent.ACTION_UP: {
        return false;
      }
      default: {
        return false;
      }
    }
  }
}
