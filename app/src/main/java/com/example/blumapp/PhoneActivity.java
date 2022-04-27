package com.example.blumapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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

public class PhoneActivity extends AppCompatActivity {

    private Button back;
    private Button back1;
    FirebaseFirestore db;
    TextView name;
    String Username;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);

        Intent intent = getIntent();
        Username = intent.getStringExtra("uName");


        db = FirebaseFirestore.getInstance();

        if(player == null){
            player = MediaPlayer.create(PhoneActivity.this, R.raw.phone);
        }
        player.start();


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("Username").equals(Username)){
                                    //Toast.makeText(PhoneActivity.this, document.getString("Name"), Toast.LENGTH_SHORT).show();
                                    name = (TextView) findViewById(R.id.textView9);
                                    name.setBackgroundColor(Color.TRANSPARENT);
                                    name.setText(document.getString("Name"));
                                }

                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });






        back = (Button) findViewById(R.id.button10);
        back.setBackgroundColor(Color.TRANSPARENT);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player != null){
                    player.release();
                    player = null;

                }


                openBack();
            }
        });


        back1 = (Button) findViewById(R.id.button11);
        back1.setBackgroundColor(Color.TRANSPARENT);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player != null){
                    player.release();
                    player = null;

                }

                openBack();
            }
        });




    }


    public void openBack(){

        Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
        intent.putExtra("uName", Username);
        startActivity(intent);

    }


}
