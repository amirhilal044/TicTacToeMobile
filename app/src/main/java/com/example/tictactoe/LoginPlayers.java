package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class LoginPlayers extends AppCompatActivity {

    Button startGame;
    EditText player1,player2;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_players);
        mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        startGame = findViewById(R.id.button_start);

        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(view -> {
            Intent goBack=new Intent(LoginPlayers.this,MainActivity.class);
            startActivity(goBack);
        });

        startGame.setOnClickListener(view -> {

            player1 = findViewById(R.id.tvPlayer1NameLog);
            player2 = findViewById(R.id.tvPlayer2NameLog);
            String _player1 = player1.getText().toString().trim();
            String _player2 = player2.getText().toString().trim();


            if(_player1.length() == 0 || _player2.length() == 0 )
            {
                String messageUsernames = "Enter All Required Inputs!";
                Toast.makeText(LoginPlayers.this, messageUsernames, Toast.LENGTH_LONG).show();
            } else{

                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean player1Exists = dataSnapshot.hasChild(_player1);
                        boolean player2Exists = dataSnapshot.hasChild(_player2);

                        if (player1Exists && player2Exists) {
                            Intent i = new Intent(LoginPlayers.this, Game.class);
                            i.putExtra("PL1", _player1);
                            i.putExtra("PL2", _player2);
                            startActivity(i);
                        } else if (player1Exists) {
                            // Only player 1 exists
                            Toast.makeText(getApplicationContext(), "Player 2 not found", Toast.LENGTH_LONG).show();
                        } else if (player2Exists) {
                            // Only player 2 exists
                            Toast.makeText(getApplicationContext(), "Player 1 not found", Toast.LENGTH_LONG).show();
                        } else {
                            // Neither player 1 nor player 2 exist
                            Toast.makeText(getApplicationContext(), "Both players not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error connecting to the database", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

    }
}