package com.tictactoe2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// This code was heavily influenced by the code base here: https://www.geeksforgeeks.org/how-to-build-a-tic-tac-toe-game-in-android/
public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    
    // 0 == X
    // 1 == O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};


    // Win Positions 
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    // this function will be called every time a
    // players tap in an empty box of the grid
    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());

        // game reset function will be called
        // if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            // increase the counter
            // after every tap
            counter++;

            // check if its the last box
            if (counter == 9) {
                // reset the game
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;

            // this will give a motion
            // effect to the image
            img.setTranslationY(-1000f);

            // change the active player
            // from 0 to 1 or 1 to 0
            if (activePlayer == 0) {
                // set the image of x
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText("O's Turn.");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText("X's Turn.");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if any player has won
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // You are the winner!!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "X has won";
                } else {
                    winnerStr = "O has won";
                }
                // Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        // If neither player wins, it is a CAT Game.
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("               CAT GAME\n(Cat's cannot catch their tail.\nYou cannot win a tied game.)");
        }
    }

    // reset the game
    public void gameReset(View view) {
        int[] imageViews = new int[]{R.id.imageView0, R.id.imageView1, R.id.imageView2,
                R.id.imageView3, R.id.imageView4, R.id.imageView5, R.id.imageView6,
                R.id.imageView7, R.id.imageView8};
        gameActive = true;
        counter = 0;
        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
            // remove all the images from the boxes inside the grid
            ((ImageView) findViewById(imageViews[i])).setImageResource(0);
        }

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
