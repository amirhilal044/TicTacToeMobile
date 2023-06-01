package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderBoard extends AppCompatActivity {
    private ListView lvLeaderboard;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        lvLeaderboard = findViewById(R.id.lvLeaderboard);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        lvLeaderboard.setAdapter(adapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://tictactoemobile-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }

                // sort the users in descending order by their scores
                Collections.sort(users, new Comparator<User>() {
                    @Override
                    public int compare(User user1, User user2) {
                        return Integer.compare(user2.getScore(), user1.getScore());
                    }
                });

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors.
            }
        });

        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LeaderBoard.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
