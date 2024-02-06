package com.apptoolsversion1.speakfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textViewWelcome;
    ImageView imageViewSpeak;
    ImageView imageViewText;
    ImageView imageViewRecord;
    ImageView imageViewFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewWelcome = findViewById(R.id.textViewWelcomeText);
        imageViewSpeak = findViewById(R.id.imageViewSpeak);
        imageViewText = findViewById(R.id.imageViewText);
        imageViewRecord = findViewById(R.id.imageViewRecord);
        imageViewFlash = findViewById(R.id.imageViewFlash);
        Toast.makeText(this, getText(R.string.welcomeText), Toast.LENGTH_SHORT).show();


        imageViewSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getText(R.string.speakTitle), Toast.LENGTH_SHORT).show();
               Intent  intent = new Intent(MainActivity.this, SpeakActivity.class);
                startActivity(intent);
            }
        });

        imageViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getText(R.string.writeTitle), Toast.LENGTH_SHORT).show();
             Intent   intent = new Intent(MainActivity.this, TextActivity.class);
                startActivity(intent);
            }
        });

        imageViewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getText(R.string.recordTitle), Toast.LENGTH_SHORT).show();
            Intent    intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        imageViewFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getText(R.string.leightTitle), Toast.LENGTH_SHORT).show();
             Intent   intent = new Intent(MainActivity.this, FlashActivity.class);
                startActivity(intent);
            }
        });

    }
}