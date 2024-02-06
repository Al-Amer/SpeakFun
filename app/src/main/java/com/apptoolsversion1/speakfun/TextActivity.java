package com.apptoolsversion1.speakfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class TextActivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    TextView textViewTitle;
    EditText editTextText;
    TextView textViewSpitch;
    SeekBar seekBarSpitch;
    TextView textViewSpeed;
    SeekBar seekBarSpeed;
    Button buttonSayIt;
    Button buttonSend;
    Button buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        textViewTitle = findViewById(R.id.titleTextToSpeech);
        editTextText = findViewById(R.id.editTextTextMultiLineTestToSpeech);
        textViewSpitch = findViewById(R.id.texttospechPitch);
        seekBarSpitch = findViewById(R.id.seekBarPitch);
        textViewSpeed = findViewById(R.id.textToSpeechSpeed);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        buttonSayIt = findViewById(R.id.TextToSpeechbuttonSayIt);
        buttonSend = findViewById(R.id.TextToSpeechbuttonback);
        buttonBack = findViewById(R.id.TextToSpeechbuttonSend);
        textViewTitle.setText(R.string.writeTitle);
        textViewSpitch.setText(R.string.TextToSpeechPitch);
        textViewSpeed.setText(R.string.TextToSpeechSpeed);
        buttonSayIt.setText(R.string.TextToSpeechSayIt);
        buttonSend.setText(R.string.buttonSend);
        buttonBack.setText(R.string.buttonBack);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.forLanguageTag(Locale.getDefault().getLanguage()));
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", getString(R.string.LanguageNotSupported));
                    } else {
                        buttonSayIt.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", getString(R.string.InitializationFailed));
                }
            }
        });
        buttonSayIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(TextActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(textToSpeech));
                sendIntent.setType("audio/*");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }
 //   String text = editTextText.getText().toString();
    private void speak(){
        String text = editTextText.getText().toString();
        float pitch = (float) seekBarSpitch.getProgress()/50;
        if (pitch < 0.1){
            pitch = 0.1f;
        }
        float speed = (float) seekBarSpeed.getProgress()/50;
        if (speed < 0.1){
            speed = 0.1f;
        }
        textToSpeech.setPitch(pitch);
        textToSpeech.setSpeechRate(speed);
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    @Override
    protected void onDestroy() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }



}