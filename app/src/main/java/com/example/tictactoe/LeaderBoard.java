package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    private ArrayAdapter<User> adapter;
    private final ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        ListView lvLeaderboard = findViewById(R.id.lvLeaderboard);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lvLeaderboard.setAdapter(adapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }

                // sort the users in descending order by their scores
                users.sort((user1, user2) -> Integer.compare(user2.getScore(), user1.getScore()));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(view -> {
            Intent i = new Intent(LeaderBoard.this, MainActivity.class);
            startActivity(i);
        });
    }
}
