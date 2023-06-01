package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPlayer extends AppCompatActivity {
    private EditText editTextUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);

        editTextUsername = findViewById(R.id.editTextUsername);
        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                if (!username.isEmpty()) {
                    registerNewUser(username);
                    Intent i = new Intent(RegisterPlayer.this, MainActivity.class);
                    startActivity(i);
                } else {
                    // Show error, username cannot be empty
                }
            }
        });

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterPlayer.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerNewUser(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        User user = new User(username, 0);
        mDatabase.child("Users").child(user.getUsername()).setValue(user);
    }
}
