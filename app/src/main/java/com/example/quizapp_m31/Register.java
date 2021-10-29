package com.example.quizapp_m31;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference userDbRef;
    LinearLayout signUp;
    FusedLocationProviderClient client;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView depToLogin;
        depToLogin = findViewById(R.id.tologin);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        Button btnSignUp = (Button) findViewById(R.id.bregister);
        EditText inputLogin = (EditText) findViewById(R.id.etLogin);
        EditText inputEmail = (EditText) findViewById(R.id.etEmail);
        EditText inputPassword = (EditText) findViewById(R.id.etPassword);
        EditText inputConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);


        depToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = inputLogin.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();


                if (TextUtils.isEmpty(login)) {
                    Toast.makeText(getApplicationContext(), "Enter login!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password confirmation is not the same!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user_id = auth.getCurrentUser().getUid();
                            int totalScore = 0;


                            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Register.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();
                            User user = new User(login,email , totalScore , latitude , longitude);
                            userDbRef.child(user_id).setValue(user);
                            startActivity(new Intent(Register.this, Login.class));
                            finish();



                            Snackbar.make(signUp, "Inscription effectuée", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }


    }
