package hu.ait.tictactoe;

import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import hu.ait.tictactoe.model.TicTacToeModel;
import hu.ait.tictactoe.view.TicTacToeView;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;
    private Chronometer chrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);
        chrono = (Chronometer) findViewById(R.id.chrono);






        final TicTacToeView gameView = (TicTacToeView) findViewById(R.id.gameView);


        Button btnClear = (Button) findViewById(R.id.btnClear);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameView.resetGame();
            }
        });

        ShimmerFrameLayout shimmerFrameLayout =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
    }

    public void setMessage(String text){

        tvData.setText(text);
        Snackbar.make(((LinearLayout)findViewById(R.id.activity_main)),
                text, Snackbar.LENGTH_LONG).show();
    }

    public void resetTime(){
        chrono.stop();
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

    }

    public void stopTime(){
        chrono.stop();
    }

}
