package com.example.tictactoe;

public class User {
    private String username;
    private int score;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, int score) {
        this.username = username.toLowerCase();
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void incrementScore() {
        this.score += 1;
    }

    @Override
    public String toString() {
        return username + " - " + score;
    }
}

