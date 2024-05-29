package com.example.bai5_new;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private Button stopButton;
    private boolean isPrepared = false;
    private Disposable mediaPlayerDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setOnPreparedListener(mp -> onMediaPrepared());
        mediaPlayer.setOnCompletionListener(mp -> onMediaCompletion());

        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);

        playButton.setOnClickListener(v -> {
            if (mediaPlayerDisposable != null) {
                mediaPlayerDisposable.dispose();
            }
            mediaPlayerDisposable = playMusic();
        });

        stopButton.setOnClickListener(v -> {
            if (mediaPlayerDisposable != null) {
                mediaPlayerDisposable.dispose();
            }
            stopMusic();
        });
    }

    private void onMediaPrepared() {
        isPrepared = true;
    }

    private void onMediaCompletion() {
        isPrepared = false;
    }

    private Disposable playMusic() {
        return Observable.create(emitter -> {
                    try {
                        if (!mediaPlayer.isPlaying() && !isPrepared) {
                            mediaPlayer.prepare();
                        }
                        mediaPlayer.start();
                        emitter.onNext(Unit.INSTANCE);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        unit -> { /* onNext */ },
                        Throwable::printStackTrace
                );
    }

    private void stopMusic() {
        Observable.create(emitter -> {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset(); // Reset the MediaPlayer after stopping
                        }
                        emitter.onNext(Unit.INSTANCE);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        unit -> { /* onNext */ },
                        Throwable::printStackTrace
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayerDisposable != null) {
            mediaPlayerDisposable.dispose();
        }
        mediaPlayer.release();
    }
}