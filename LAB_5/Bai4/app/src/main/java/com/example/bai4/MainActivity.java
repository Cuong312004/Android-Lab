package com.example.bai4;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button stopButton;
    private boolean isPrepared = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.music);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                onMediaPrepared();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onMediaCompletion();
            }
        });

        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);

        playButton.setOnClickListener(v -> new PlayMusicTask().execute());
        stopButton.setOnClickListener(v -> new StopMusicTask().execute());
    }

    private void onMediaPrepared() {
        isPrepared = true;
    }

    private void onMediaCompletion() {
        isPrepared = false;
    }

    private class PlayMusicTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (!mediaPlayer.isPlaying() && !isPrepared) {
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class StopMusicTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset(); // Reset the MediaPlayer after stopping
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}