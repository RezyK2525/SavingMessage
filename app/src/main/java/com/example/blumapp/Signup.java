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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private Button mainActivity;
    private Button LoginBut;
    FirebaseFirestore db;
    EditText username, password1, password2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);






        username = findViewById(R.id.Username);
        password1 = findViewById(R.id.Password1);
        password2 = findViewById(R.id.Password2);






        mainActivity = (Button) findViewById(R.id.button2);
        mainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("abc", "HI");

                db = FirebaseFirestore.getInstance();
                String Username = username.getText().toString();
                String Password1 = password1.getText().toString();
                String Password2 = password2.getText().toString();
                if(Username.equals("")||Password1.equals("")|Password2.equals("")){
                    Toast.makeText(Signup.this, "Please fill in all fields", Toast.LENGTH_LONG).show();

                }else{



                if(Password1.equals(Password2)){


                db.collection("users").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                boolean exists = false;
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("Username").equals(Username)){
                                            exists = true;
                                            Toast.makeText(Signup.this, "Username already exists try another username.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    if(!exists){



                                            Map<String,Object> users = new HashMap<>();
                                            users.put("Username", Username);
                                            users.put("Password", Password1);
                                            users.put("Title", "Default Prefab");
                                            users.put("Name", "Boss");



                                            db.collection("users").add(users)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            openMain();
                                                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w("TAG", "Error adding document", e);
                                                        }
                                                    });

                                            //Toast.makeText(Signup.this, "Add to DB", Toast.LENGTH_SHORT).show();

                                    }

                                } else {
                                    Log.w("TAG", "Error getting documents.", task.getException());
                                }
                            }
                        });

                }else{
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                }
                }



                //Toast.makeText(Signup.this, users.toString(), Toast.LENGTH_SHORT).show();






                //openLogin();
            }
        });




        LoginBut = (Button) findViewById(R.id.button15);
        LoginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });


    }


    public void openLogin(){

        Intent intent = new Intent(Signup.this, LoginActivity.class);
        startActivity(intent);

    }

    public void openMain(){

        Intent intent = new Intent(Signup.this, MainActivity.class);
        intent.putExtra("uName", username.getText().toString());
        startActivity(intent);

    }



}