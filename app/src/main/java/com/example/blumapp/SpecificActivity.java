package com.example.blumapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class SpecificActivity extends AppCompatActivity {

    private Button back;
    private Button save;
    EditText titleText, nameText;
    String Username;
    FirebaseFirestore db;
    String Title, Name, ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_field);

        Intent intent = getIntent();
        Username = intent.getStringExtra("uName");

        db = FirebaseFirestore.getInstance();


        back = (Button) findViewById(R.id.button4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMain();
            }
        });

        save = (Button) findViewById(R.id.button5);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                titleText = findViewById(R.id.TitleText);
                Title = titleText.getText().toString();

                nameText = findViewById(R.id.NameText);
                Name = nameText.getText().toString();


                //Toast.makeText(SpecificActivity.this, docRef.toString(), Toast.LENGTH_SHORT).show();


                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if(document.getString("Username").equals(Username)){
                                            ID = document.getId();
                                            //Toast.makeText(LoginActivity.this, "Correct Login", Toast.LENGTH_SHORT).show();
                                            db.collection("users").document(ID)
                                                    .update(
                                                            "Title", Title,
                                                            "Name", Name

                                                    );
                                            //document.toObject("Title", Title)

                                            openMain();

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



    public void openMain(){

        Intent intent = new Intent(SpecificActivity.this, MainActivity.class);
        intent.putExtra("uName", Username);
        startActivity(intent);

    }


}
