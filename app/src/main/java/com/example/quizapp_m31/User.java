package com.example.quizapp_m31;

public class User {

    public String username;
    public String email;
    public int totalScore;
    public double latitude;
    public double longitude;

    public User() {
    }

    public User(String username, String email, int totalScore , double latitude , double longitude) {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.username = username;
        this.email = email;
        this.totalScore = totalScore;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
