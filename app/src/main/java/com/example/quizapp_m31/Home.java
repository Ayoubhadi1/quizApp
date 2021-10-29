package com.example.quizapp_m31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    private FirebaseAuth auth;
    Button btnlogout , btnQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnlogout = findViewById(R.id.btnlogout);
        btnQuiz = findViewById(R.id.btnQuiz);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this , Quiz.class);
                startActivity(intent);
            }
        });
    }
}