package hu.ait.tictactoe.model;

import android.view.View;
import android.widget.Chronometer;

import hu.ait.tictactoe.R;
import hu.ait.tictactoe.view.TicTacToeView;

/**
 * Created by zberkowitz on 2/23/17.
 */



public class TicTacToeModel {

    private static TicTacToeModel instance = null;




    private TicTacToeModel(){


    }

    public static TicTacToeModel getInstance(){
        if(instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    private static short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    public void resetModel() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                model[i][j] = EMPTY;
            }
        }

        nextPlayer = CIRCLE;
    }

    public short checkWinner(){
        short winner = EMPTY;

        for (int i = 0; i < 3 ; i++) {
            if(model[i][0] == model[i][1] && model[i][1] == model[i][2]){
                winner = model[i][0];

                return winner;

            }
        }

        for (int j = 0; j < 3 ; j++) {
            if(model[0][j] == model[1][j] && model[1][j] == model[2][j]){
                winner = model[0][j];

                return winner;

            }
        }

        if(model[0][0] == model[1][1] && model[1][1] == model[2][2]) {
            winner = model[0][0];

        }
        else if(model[0][2] == model[1][1] && model[1][1] == model[2][0]){
            winner = model[2][0];


        }

        return winner;


    }



    public void changeNextPlayer(){
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;

    }

    public static void setField(int x, int y, short player){
        model[x][y] = player;
    }

    public short getField(int x, int y){
        return model[x][y];
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

}
