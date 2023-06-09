package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    Button startGame, instructions, registerPlayer, leaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGame = findViewById(R.id.btnStartGame);
        instructions = findViewById(R.id.btnInstructions);
        registerPlayer = findViewById(R.id.btnRegisterNewPlayer);
        leaderboard = findViewById(R.id.btnLeaderBoard);

        startGame.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,LoginPlayers.class);
            startActivity(i);
        });
        instructions.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,Instructions.class);
            startActivity(i);
        });
        registerPlayer.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,RegisterPlayer.class);
            startActivity(i);
        });
        leaderboard.setOnClickListener(view -> {
            Intent i=new Intent(MainActivity.this,LeaderBoard.class);
            startActivity(i);
        });
    }
}