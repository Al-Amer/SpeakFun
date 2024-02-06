package com.apptoolsversion1.speakfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    TextView textViewTitle;
    Button buttonStartRecord;
    Button buttonStopRecord;
    Button buttonStartPlay;
    Button buttonStopPlay;
    Button buttonBack;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    String AudioSavePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        textViewTitle = findViewById(R.id.textViewRecord);
        buttonStartRecord = findViewById(R.id.buttonRecordStartRecord);
        buttonStopRecord = findViewById(R.id.buttonRecordStopRecord);
        buttonStartPlay = findViewById(R.id.buttonRecordStartPlay);
        buttonStopPlay = findViewById(R.id.buttonRecordStopPlay);
        buttonBack = findViewById(R.id.buttonRecordButtonBack);

        if (isMicrophone()){
            getMicrophonePermission();
        }

        buttonStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(getRecorderFilePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(RecordActivity.this, getText(R.string.recordingStarted), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        buttonStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                Toast.makeText(RecordActivity.this, getText(R.string.recordingStopped), Toast.LENGTH_SHORT).show();
            }
        });
        buttonStartPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mediaPlayer = new MediaPlayer();
                 //   mediaPlayer.setDataSource(AudioSavePath);
                    mediaPlayer.setDataSource(getRecorderFilePath());
                    if (mediaPlayer != null){
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Toast.makeText(RecordActivity.this, getText(R.string.startPlay), Toast.LENGTH_SHORT).show();
                    }else if (mediaPlayer == null){
                        Toast.makeText(RecordActivity.this, getText(R.string.noRecordVoiceMessage), Toast.LENGTH_SHORT).show();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
                Toast.makeText(RecordActivity.this, getText(R.string.stopPlay), Toast.LENGTH_SHORT).show();
            }else if (mediaPlayer == null){
                Toast.makeText(RecordActivity.this, getText(R.string.noRecordVoiceMessage), Toast.LENGTH_SHORT).show();
            }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(RecordActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });

    }
    private boolean checkPermission(){
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int secont = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return first == PackageManager.PERMISSION_GRANTED && secont == PackageManager.PERMISSION_GRANTED;
    }
    private boolean isMicrophone(){
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }else {
            return false;
        }
    }
    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO},200);
            }
        }
    private String getRecorderFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "Voice Record"+".mp3");
        return file.getPath();
    }


}