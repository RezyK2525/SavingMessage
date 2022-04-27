package com.example.blumapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {

    private Button specific;
    private Button phone;

    String Username;
    FirebaseFirestore db;
    TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Username = intent.getStringExtra("uName");
        Toast.makeText(MainActivity.this, "Welcome "+ Username, Toast.LENGTH_LONG).show();


        db = FirebaseFirestore.getInstance();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("Username").equals(Username)){
                                    //Toast.makeText(LoginActivity.this, "Correct Login", Toast.LENGTH_SHORT).show();
                                    document.getString("Title");
                                    Title = (TextView) findViewById(R.id.textView);
                                    Title.setText(document.getString("Title"));
                                }

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });




        phone = (Button) findViewById(R.id.button7);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhone();
            }
        });

        specific = (Button) findViewById(R.id.button);
        specific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSpecific();
            }
        });

    }



    public void openSpecific(){

        Intent intent = new Intent(MainActivity.this, SpecificActivity.class);
        intent.putExtra("uName", Username);
        startActivity(intent);

    }

    public void openPhone(){

        Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
        intent.putExtra("uName", Username);
        startActivity(intent);

    }



}