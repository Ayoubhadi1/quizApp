package com.example.quizapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.Objects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;

public class Quiz extends AppCompatActivity {

    // Create a Cloud Storage reference from the app
    private StorageReference storageRef ;
    private ImageView imageView;
    private Button btnValider;
    private RadioGroup radioGroup;
    int counter = 1;
    int scoreQuiz = 0;
    private int i=0;
    ArrayList<ArrayList<DatabaseReference>> quizArrayRef = new ArrayList<>() ;
    DatabaseReference quizRef[][] = new DatabaseReference[6][6] ;
    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReferenceQst , dbRefOpt1 , dbRefOpt2 , dbRefOpt3;

    // variable for Text view.
    private TextView etQuestion , TitleQuiz;
    private RadioButton opt1 , opt2 , opt3 , radioCheked ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        storageRef = FirebaseStorage.getInstance().getReference();
        imageView = (ImageView) findViewById(R.id.imageView);
        opt1 = (RadioButton) findViewById(R.id.option1);
        opt2 = (RadioButton) findViewById(R.id.option2);
        opt3 = (RadioButton) findViewById(R.id.option3);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        btnValider = (Button) findViewById(R.id.btnValider);

        // initializing our object class variable.
        etQuestion = findViewById(R.id.etQuestion);
        TitleQuiz = findViewById(R.id.TitleQuiz);



        // below line is used to get the instance
        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();





        for (int i=0 ; i<6 ; i++){
            quizRef[i][0] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("image");
            quizRef[i][1] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("question");
            quizRef[i][2] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("option1");
            quizRef[i][3] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("option2");
            quizRef[i][4] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("option3");
            quizRef[i][5] = firebaseDatabase.getReference("Quiz").child("Quiz"+(i+1)).child("reponse");

        }




       getData(i);
        TitleQuiz.setText("Quiz "+counter);
            btnValider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!opt1.isChecked() && !opt2.isChecked() && !opt3.isChecked()){
                        Toast.makeText(getApplicationContext(), "Veuillez choisir une réponse !", Toast.LENGTH_SHORT).show();
                    }else{


                        checkScore(i);
                        opt1.setChecked(false);
                        opt2.setChecked(false);
                        opt3.setChecked(false);
                        i++;
                        counter++;

                        if(i < 6){
                            TitleQuiz.setText("Quiz "+counter);
                            getData(i);

                        }
                        else{

                            Intent myIntent = new Intent(Quiz.this, Score.class);
                            myIntent.putExtra("intVariableName", scoreQuiz);
                            startActivity(myIntent);
                        }



                        /*if (i == 6) {
                            //startActivity(new Intent(Quiz.this, Score.class));

                            Intent myIntent = new Intent(Quiz.this, Score.class);
                            myIntent.putExtra("intVariableName", scoreQuiz);
                            startActivity(myIntent);

                        }*/

                    }

                }
            });











    }

    public void checkScore(int i){
        quizRef[i][5].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioCheked = (RadioButton) findViewById(selectedId);
                if(radioCheked.getText().equals(value)){
                    scoreQuiz++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData(int i) {


        quizRef[i][0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Glide.with(Quiz.this)
                        .load(value)
                        .into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        quizRef[i][1].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                etQuestion.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        quizRef[i][2].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                opt1.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        quizRef[i][3].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                opt2.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        quizRef[i][4].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                opt3.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Quiz.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }








}