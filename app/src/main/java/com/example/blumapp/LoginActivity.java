package com.example.blumapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    EditText uName, pWord;
    private Button signupButton;
    private Button loginButton;

    FirebaseFirestore db;

    String USERname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        uName = findViewById(R.id.Username);
        pWord = findViewById(R.id.Password1);

        db = FirebaseFirestore.getInstance();

        signupButton = (Button) findViewById(R.id.button2);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                USERname = uName.getText().toString();
                String username = uName.getText().toString();
                String password = pWord.getText().toString();

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("Username").equals(username) && document.getString("Password").equals(password)){
                                            //Toast.makeText(LoginActivity.this, "Correct Login", Toast.LENGTH_SHORT).show();
                                            openMain();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        });





            }
        });


    }

    public void openSignUp(){

        Intent intent = new Intent(LoginActivity.this, Signup.class);
        startActivity(intent);

    }

    public void openMain(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("uName", USERname);
        startActivity(intent);

    }



}