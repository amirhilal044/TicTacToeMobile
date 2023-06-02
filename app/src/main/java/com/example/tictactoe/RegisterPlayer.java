package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterPlayer extends AppCompatActivity {
    private EditText etUsername;
    Button register;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);


        register = findViewById(R.id.button_register);
        mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        etUsername = findViewById(R.id.etPlayerUserName);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim().toLowerCase(); // Move the assignment inside onClick()

                if(username=="") {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                }else{
                    mDatabase.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // User already exists
                                Toast.makeText(getApplicationContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                            } else {
                                // Register the new user
                                registerNewUser(username);
                                etUsername.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Error occurred while reading the data
                            Toast.makeText(getApplicationContext(), "Error registering new player", Toast.LENGTH_SHORT).show();
                        }
                    });
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
