package com.example.kevin.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private int userGlobalScore;
    private int userTurnScore;
    private int computerGlobalScore;
    private int computerTurnScore;

    private Button rollButton;
    private Button holdButton;
    private Button resetButton;

    private ImageView image;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice_default);

        rollButton = (Button) findViewById(R.id.roll);
        holdButton = (Button) findViewById(R.id.hold);
        resetButton = (Button) findViewById(R.id.reset);
        image = (ImageView) findViewById(R.id.dice);
        random = new Random();

        userGlobalScore = 0;
        userTurnScore = 0;
        computerGlobalScore = 0;
        computerTurnScore = 0;

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = random.nextInt(6)+1;
                updateGame(value, 0);

            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollButton.setEnabled(false);
                holdButton.setEnabled(false);
                userGlobalScore += userTurnScore;
                TextView view = (TextView) findViewById(R.id.yourScore);
                view.setText("Your Score: " + userGlobalScore);

                view = (TextView) findViewById(R.id.turn);
                if(userGlobalScore>=100){
                    view.setText("You Won!");
                }else {
                    view.setText("You Scored " + userTurnScore + " Points!\n\nComputer's Turn");
                    userTurnScore = 0;


                    Handler timerHandler = new Handler();
                    timerHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerTurn();
                        }
                    }, 1000);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userGlobalScore = 0;
                userTurnScore = 0;
                computerGlobalScore = 0;
                computerTurnScore = 0;

                TextView view = (TextView) findViewById(R.id.yourScore);
                view.setText("Your Score: 0");

                view = (TextView) findViewById(R.id.computerScore);
                view.setText("Computer's Score: 0");

                view = (TextView) findViewById(R.id.turn);
                view.setText("Your Turn Score: 0");

                rollButton.setEnabled(true);
                holdButton.setEnabled(true);
            }
        });
    }

    public void updateGame(int value, int turn){
        switch(value){
            case 1:
                image.setImageResource(R.drawable.dice1);
                Handler timerHandler = new Handler();
                rollButton.setEnabled(false);
                holdButton.setEnabled(false);
                timerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        computerTurn();
                    }
                }, 1000);
                TextView view = (TextView) findViewById(R.id.turn);
                view.setText("You Rolled a 1\nComputer's turn");
                userTurnScore = 0;
                break;
            case 2:
                image.setImageResource(R.drawable.dice2);
                updateTurn(value, turn);
                break;
            case 3:
                image.setImageResource(R.drawable.dice3);
                updateTurn(value, turn);
                break;
            case 4:
                image.setImageResource(R.drawable.dice4);
                updateTurn(value, turn);
                break;
            case 5:
                image.setImageResource(R.drawable.dice5);
                updateTurn(value, turn);
                break;
            case 6:
                image.setImageResource(R.drawable.dice6);
                updateTurn(value, turn);
                break;
        }
    }


    public void updateTurn(int value, int turn){
        TextView view = (TextView) findViewById(R.id.turn);
        if(turn == 0){
            userTurnScore += value;
            view.setText("Your Turn Score: " + userTurnScore);
        }else{
            computerTurnScore += value;
            view.setText("Computer Rolled a " + value +"\n\nComputer Turn Score: " + computerTurnScore);
        }
    }


    public void computerTurn(){

        boolean checker = true;
        Handler timerHandler = new Handler();

        int value = random.nextInt(6)+1;
        if(value == 1){
            TextView view = (TextView) findViewById(R.id.turn);
            view.setText("Computer Rolled a 1");
            computerTurnScore = 0;
            checker = false;
            rollButton.setEnabled(true);
            holdButton.setEnabled(true);
        }else {
            updateGame(value, 1);

            if (computerTurnScore >= 15 || computerTurnScore + computerGlobalScore >= 100) {

                computerGlobalScore += computerTurnScore;
                TextView view = (TextView) findViewById(R.id.turn);
                view.setText("Computer Scored " + computerTurnScore);
                view = (TextView) findViewById(R.id.computerScore);
                view.setText("Computer's Score: " + computerGlobalScore);
                computerTurnScore = 0;
                checker = false;
                if(computerGlobalScore>= 100){
                    view = (TextView) findViewById(R.id.turn);
                    view.setText("The Computer Has Won!");

                }else{
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }

            }else if(userGlobalScore >= 80 && computerGlobalScore < userGlobalScore && computerGlobalScore + computerTurnScore > userGlobalScore){
                computerGlobalScore += computerTurnScore;
                TextView view = (TextView) findViewById(R.id.turn);
                view.setText("Computer Scored " + computerTurnScore);
                view = (TextView) findViewById(R.id.computerScore);
                view.setText("Computer's Score: " + computerGlobalScore);
                computerTurnScore = 0;
                checker = false;
                if(computerGlobalScore>= 100){
                    view = (TextView) findViewById(R.id.turn);
                    view.setText("The Computer Has Won!");

                }else{
                    rollButton.setEnabled(true);
                    holdButton.setEnabled(true);
                }
            }
        }

        if(checker){
            timerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            }, 1000);
        }
    }


}
