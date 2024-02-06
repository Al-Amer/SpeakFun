package com.apptoolsversion1.speakfun;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeakActivity extends AppCompatActivity {

    TextView textViewTitle;
    ImageView imageViewSpeak;
    Button buttonSend;
    Button buttonBack;
    EditText editTextResult;
   String textSpeaking;
    protected static final int RESULT_SPEECH = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);

        textViewTitle = findViewById(R.id.textViewTitle);
        imageViewSpeak = findViewById(R.id.SpeakLayoutimageViewSpeak);
        buttonBack = findViewById(R.id.buttonSpeakBack);
        buttonSend = findViewById(R.id.buttonSpeakSend);
        editTextResult = findViewById(R.id.editTextTextMultiLine);
        textSpeaking = getString(R.string.StartSpeaking);

        imageViewSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, textSpeaking);
                  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault().getLanguage().toString());
                Toast.makeText(SpeakActivity.this, textSpeaking, Toast.LENGTH_SHORT).show();
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    editTextResult.setText("");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getText(R.string.WarMesageSpeakToText), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(SpeakActivity.this, MainActivity.class);
                startActivity(intentBack);

            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editTextResult.getText());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editTextResult.setText(text.get(0));
                }
                break;
        }


    }



}