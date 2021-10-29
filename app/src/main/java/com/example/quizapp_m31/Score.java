package com.example.quizapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.EventListener;

public class Score extends AppCompatActivity {
    TextView tvScore , TitleScore;
    DatabaseReference firebaseDatabase;
    ProgressBar pbBarre;
    Button btnRecommencer , btnQuitter ,btnMap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore = (TextView) findViewById(R.id.score);
        TitleScore = (TextView) findViewById(R.id.TitleScore);
        pbBarre = (ProgressBar) findViewById(R.id.progressBar) ;
        btnRecommencer = (Button)findViewById(R.id.btnRecommencer);
        btnQuitter = (Button)findViewById(R.id.btnQuitter);
        btnMap = (Button)findViewById(R.id.btnMap);

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("intVariableName", 0);
        tvScore.setText("Votre score est "+String.valueOf(intValue)+" / 6");

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String uidCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase.child(uidCurrentUser).child("totalScore").setValue(ServerValue.increment(intValue));

        int sc = (int)(intValue*100/6);
        tvScore.setText("Ton score est : "+String.valueOf(sc)+"%");
        pbBarre.setProgress(sc);

        btnRecommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Score.this, Home.class));
            }
        });

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Score.this, MapsActivity.class));
            }
        });








    }
}