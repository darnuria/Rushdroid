package android.rushdroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.rushdroid.model.Direction;
import android.rushdroid.model.Model;
import android.rushdroid.model.Piece;
import android.rushdroid.model.Position;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Corentin-Axel on 08/02/16.
 * Classe responsible of the rendering. Use a GameThread for drawing of the UI Thread.
 * @see GameThread
 */

final public class GameView extends SurfaceView {
  private int surface_width;
  private int surface_height;
  private final int game_width = 6;
  private final int game_height = 6;
  private final int[] xs = new int[game_width];
  private final int[] ys = new int[game_height];
  private Integer current = null;
  private final Model m;
  private final Bitmap[] bitmaps = new Bitmap[6];
  private Piece down_p = null;
  private int down_x;
  private int down_y;

  private void fillArray(int[] a, int game_size, int surface_size) {
    int d = game_size / surface_size;
    a[0] = 1;
    for (int i = 1; i < a.length; i++) {
      a[i] = d * i;
    }
  }

  public GameView(@NonNull Context context, @NonNull AttributeSet attrs) {
    super(context, attrs);

    this.bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.vertical2);
    this.bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.vertical3);
    this.bitmaps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.horizontal2);
    this.bitmaps[3] = BitmapFactory.decodeResource(getResources(), R.drawable.horizontal3);
    this.bitmaps[4] = BitmapFactory.decodeResource(getResources(), R.drawable.main_block);
    this.bitmaps[5] = BitmapFactory.decodeResource(getResources(), R.drawable.background);
    this.m = ((GameApplication) context.getApplicationContext()).game();

    getHolder().addCallback(new SurfaceHolder.Callback() {
      private GameThread th;

      @Override
      public void surfaceCreated(@NonNull SurfaceHolder h) {
        this.th = new GameThread(GameView.this, h);
        surface_width = GameView.this.getWidth();
        surface_height = GameView.this.getHeight();
        fillArray(xs, surface_width, game_width);
        fillArray(ys, surface_height, game_height);
        this.th.setOn();
        this.th.start();
      }

      @Override
      public void surfaceDestroyed(@NonNull SurfaceHolder h) {
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        this.th.setOff();
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
        // Nothing to do?
      }
    });
  }

  private void drawGrid(Canvas c) {
    Paint p = new Paint();
    p.setColor(Color.RED);
    int Y = this.surface_height;
    for (int x : this.xs) {
      c.drawLine(x, 1, x, Y, p);
    }
    int X = this.surface_width;
    for (int y : this.ys) {
      c.drawLine(1, y, X, y, p);
    }
    c.drawLine(0, Y - 1, X - 1, Y - 1, p);
    c.drawLine(X - 1, 0, X - 1, X - 1, p);
  }

  private Bitmap help_draw (int id) { if (id == 0) { return this.bitmaps[4]; } else {return this.bitmaps[2]; } }

  /**
   * This function draw all the game state for each frame.
   * It will be called from the game thread.
   * @param c Canvas
   */
  private void drawGame(@NonNull Canvas c, Iterable<Piece> pieces) {
    int ratioY = this.surface_height / this.game_height;
    int ratioX = this.surface_width / this.game_width;
    for (Piece piece : pieces) {
      Position p = piece.getPos();
      int size = piece.getSize();

      if (piece.getOrientation() == Direction.VERTICAL) {
        int x2 = (p.x + 1) * ratioX;
        int y2 = (p.y + size) * ratioY;
        Bitmap bitmap = (size == 2) ? (this.bitmaps[0]) : (this.bitmaps[1]);
        c.drawBitmap(bitmap, null, new RectF(p.x * ratioX + 1, p.y * ratioY + 1, x2, y2), null);
      } else {
        int x2 = (p.x + size) * ratioX;
        int y2 = (p.y + 1) * ratioY;
        Bitmap bitmap = (size == 2) ? (help_draw(piece.getId())) : (this.bitmaps[3]);
        c.drawBitmap(bitmap, null, new RectF(p.x * ratioX + 1, p.y * ratioY + 2, x2, y2), null);
      }
    }
  }

  @Override
  public void onDraw(@NonNull Canvas c) {
    if (!m.endOfGame()) {
      c.drawBitmap(this.bitmaps[5], null, new RectF(0, 0, surface_width, surface_height), null);
      drawGame(c, m.pieces());
    } else {
      c.drawColor(Color.BLACK);
      Paint p = new Paint();
      p.setColor(Color.RED);
      // TODO: Tout regler par rapport à la taille de l'écran.
      p.setTextSize(100.f);
      c.drawText("Win!", 150.f, (float) this.surface_height / 2.f, p);
    }
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
  private Position scale(int x, int y) {
    return new Position(x * this.game_width / this.surface_width,
            y * this.game_height / this.surface_height);
  }

  /*
  // A lot of side effects.
  private boolean help_move(int prev, int now) {
    return prev <= now ? this.m.moveForward(this.current) : this.m.moveBackward(this.current);
  }
  */

  int help_move(int current, int test) {
    if (current < test && this.m.moveForward(this.current)) { return current + 1; }
    else if (current > test && this.m.moveBackward(this.current)) { return current - 1; }
    else { return current; }
  }

  private void onDown(int x, int y) {
    Position p = scale(x, y);
    this.current = this.m.getIdByPos(p);
    if (this.current != null) {
      this.down_p = m.piece(this.current);
      this.down_x = p.x;
      this.down_y = p.y;
    }
  }

  private void onMove(int x, int y) {
    if (this.current != null) {
      Position p = scale(x, y);
      if (m.getOrientation(current) == Direction.VERTICAL) {
        this.down_y = this.help_move(down_y, p.y);
      } else {
        this.down_x = this.help_move(down_x, p.x);
      }
    }
  }

  private void onUp() {
    if (this.current != null) {
      if (!down_p.getPos().equals(m.piece(current).getPos())) {
        this.m.undoPush(down_p);
        this.m.redoClear();
        this.down_p = null;
      }
      this.current = null;
    }
  }

  @Override
  public boolean onTouchEvent(@NonNull MotionEvent e) {
    int x = (int) e.getX();
    int y = (int) e.getY();
    switch (e.getAction()) {
      case MotionEvent.ACTION_DOWN: {
        // System.out.println(p);
        this.onDown(x, y);
        return true;
      } case MotionEvent.ACTION_MOVE: {
        this.onMove(x, y);
        return true;
      } case MotionEvent.ACTION_UP: {
        this.onUp();
        return true;
      }
      default: {
        return false;
      }
    }
  }
}