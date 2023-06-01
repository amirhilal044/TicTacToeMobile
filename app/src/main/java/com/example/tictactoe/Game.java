package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Game extends AppCompatActivity implements View.OnClickListener {

    private final Button[] buttons = new Button[9];
    private boolean player1Turn = true;
    private int roundCount;



    TextView player1Name, player2Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player1Name = findViewById(R.id.tvPlayer1Name);
        player2Name = findViewById(R.id.tvPlayer2Name);
        Intent i = getIntent();
        player1Name.setText(i.getStringExtra("PL1"));
        player2Name.setText(i.getStringExtra("PL2"));


        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> resetBoard());

        Button endBattle = findViewById(R.id.button_end);
        endBattle.setOnClickListener(v -> {
            Intent ending=new Intent(Game.this,MainActivity.class);
            startActivity(ending);
        });


        for(int j=0; j<9; j++) {
            String buttonID = "button_" + j;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[j] = findViewById(resID);
            buttons[j].setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")) {
            return;
        }

        if(player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if(checkForWin()) {
            if(player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                field[i][j] = buttons[(i*3) + j].getText().toString();
            }
        }

        for(int i=0; i<3; i++) {
            if(field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for(int i=0; i<3; i++) {
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        updateScore(player1Name.getText().toString());
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_LONG).show();
        resetBoard();
    }



    private void player2Wins() {
        updateScore(player2Name.getText().toString());
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetBoard();

    }

    private void resetBoard() {
        for(int i=0; i<9; i++) {
            buttons[i].setText("");
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void updateScore(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        DatabaseReference userRef = mDatabase.child("Users").child(username);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.incrementScore();
                userRef.setValue(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

}