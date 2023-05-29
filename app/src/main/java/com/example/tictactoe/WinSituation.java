package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinSituation extends AppCompatActivity {

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_situation);
        TextView winning = findViewById(R.id.tvWinning);
        //PL1WON
        //PL2WON
        winning.setText(intent.getStringExtra("WIN"));
        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(WinSituation.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}