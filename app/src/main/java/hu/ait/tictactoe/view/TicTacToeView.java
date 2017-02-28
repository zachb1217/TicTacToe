package hu.ait.tictactoe.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;

import hu.ait.tictactoe.MainActivity;
import hu.ait.tictactoe.R;
import hu.ait.tictactoe.model.TicTacToeModel;

/**
 * Created by zberkowitz on 2/20/17.
 */

public class TicTacToeView extends View {



    private Paint paintBg;
    private Paint paintLine;
    private Paint paintCircle;
    private Paint paintCross;
    private Paint paintText;

    private Bitmap bitmapBG;

    private MainActivity context = (MainActivity)getContext();

    private int winner = 0;
    private String next = "O";


    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintCircle = new Paint();
        paintCircle.setColor(Color.rgb(249,6,224));
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setStrokeWidth(20);

        paintCross = new Paint();
        paintCross.setColor(Color.BLUE);
        paintCross.setStrokeWidth(20);
        paintCross.setStyle(Paint.Style.STROKE);

        paintText = new Paint();
        paintText.setColor(Color.RED);

        bitmapBG = BitmapFactory.decodeResource(getResources(), R.drawable.bacon);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);

        bitmapBG = Bitmap.createScaledBitmap(bitmapBG, getWidth(), getHeight(), false);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0, getWidth(), getHeight(),
                paintBg);

        canvas.drawBitmap(bitmapBG, 0,0, null);





        drawGameGrid(canvas);

        drawPlayers(canvas);

    }

    private void drawPlayers(Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getField(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 8 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintCircle);

                } else if (TicTacToeModel.getInstance().getField(i,j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3 + getWidth() / 15,
                            j * getHeight() / 3 + getHeight() / 15 ,
                            (i + 1) * getWidth() / 3 - getWidth() / 15  ,
                            (j + 1) * getHeight() / 3 - getHeight() / 15, paintCross);

                    canvas.drawLine((i + 1) * getWidth() / 3 - getWidth() / 15,
                            j * getHeight() / 3  + getHeight() / 15,
                            i * getWidth() / 3  + getWidth() / 15,
                            (j + 1) * getHeight() / 3 - getHeight()/ 15, paintCross);
                }
            }
        }
    }

    private void drawGameGrid(Canvas canvas) {
        canvas.drawRect(0,0,getWidth(),getHeight(),paintLine);
        canvas.drawLine(getWidth()/3,0, getWidth()/3, getHeight(), paintLine);
        canvas.drawLine(2*getWidth()/3,0, 2*getWidth()/3, getHeight(), paintLine);
        canvas.drawLine(0, getHeight()/3, getWidth(), getHeight()/3, paintLine);
        canvas.drawLine(0, 2* getHeight()/3, getWidth(), 2* getHeight()/3, paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_DOWN) {



            int xT = (int) event.getX() / (getWidth()/3);
            int yT = (int) event.getY() / (getHeight()/3);

            if(TicTacToeModel.getInstance().getField(xT,yT) == TicTacToeModel.EMPTY && winner == 0) {

                TicTacToeModel.getInstance().setField(xT, yT,
                        TicTacToeModel.getInstance().getNextPlayer());

                TicTacToeModel.getInstance().changeNextPlayer();


                winner = TicTacToeModel.getInstance().checkWinner();

                if(winner != 0){
                    context.setMessage(
                            getResources().getString(R.string.who_won, next)
                    );

                    context.stopTime();

                }
                else {
                    if (TicTacToeModel.getInstance().getNextPlayer() == TicTacToeModel.CROSS) {
                        next = "X";
                    }
                    else {
                        next = "O";
                    }

                    context.setMessage(
                            getResources().getString(R.string.next_player, next)
                    );

                    context.resetTime();
                }



                invalidate();


            }

        }

        return true;

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame() {
        TicTacToeModel.getInstance().resetModel();
        winner = 0;
        context.stopTime();
        context.setMessage("Touch Screen to Start");
        invalidate();
    }



}
