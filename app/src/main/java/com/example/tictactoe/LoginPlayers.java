package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class LoginPlayers extends AppCompatActivity {

    Button startGame;
    EditText player1,player2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_players);


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
                Intent i = new Intent(LoginPlayers.this, Game.class);
                i.putExtra("PL1", _player1);
                i.putExtra("PL2", _player2);
                startActivity(i);
            }
        });

    }
}