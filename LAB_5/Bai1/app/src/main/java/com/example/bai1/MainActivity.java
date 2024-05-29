package com.example.bai1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
public class MainActivity extends AppCompatActivity {

    private ProgressBar pbFirst;
    private ProgressBar pbSecond;
    private TextView tvMsgWorking;
    private TextView tvMsgReturned;
    private boolean isRunning = false;
    private int MAX_SEC = 0;
    private int intTest = 0;
    private Thread bgThread;
    private Handler handler;
    private Button btnStart;

    private void findViewByIds() {
        pbFirst = findViewById(R.id.pb_first);
        pbSecond = findViewById(R.id.pb_second);
        tvMsgWorking = findViewById(R.id.tv_working);
        tvMsgReturned = findViewById(R.id.tv_return);
        btnStart = findViewById(R.id.btn_start);
    }

    private void initVariables() {
        isRunning = false;
        MAX_SEC = 20;
        intTest = 1;
        pbFirst.setMax(MAX_SEC);
        pbFirst.setProgress(0);

        // Init Views
        pbFirst.setVisibility(View.GONE);
        pbSecond.setVisibility(View.GONE);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String returnedValue = (String) msg.obj;
                // Do something with the value sent by background thread here...
                tvMsgReturned.setText(getString(R.string.returned_by_bg_thread) + returnedValue);
                pbFirst.incrementProgressBy(2);
                // Testing thread's termination
                if (pbFirst.getProgress() == MAX_SEC) {
                    System.out.println("Done");
                    tvMsgReturned.setText(getString(R.string.done_background_thread_has_been_stopped));
                    tvMsgWorking.setText(getString(R.string.done));
                    pbFirst.setVisibility(View.GONE);
                    pbFirst.setProgress(0);
                    pbSecond.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                    isRunning = false;
                } else {
                    tvMsgWorking.setText(getString(R.string.working) + pbFirst.getProgress());
                }
            }
        };
    }

    private void initBgThread() {
        bgThread = new Thread(() -> {
            try {
                int i = 0;
                while (i < MAX_SEC && isRunning) {
                    // Sleep one second
                    Thread.sleep(1000);
                    Random rnd = new Random();

                    // This is a locally generated value
                    String data = "Thread value: " + rnd.nextInt(101);

                    // We can see change (global) class variables
                    data += getString(R.string.global_value_seen) + " " + intTest;
                    intTest++;

                    // If thread is still alive send the message
                    if (isRunning) {
                        // Request a message token and put some data in it
                        Message msg = handler.obtainMessage(1, data);
                        handler.sendMessage(msg);
                    }
                    i++;
                }
            } catch (Throwable t) {
                // Handle the exception
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        initVariables();

        // Handle clickListener
        btnStart.setOnClickListener(v -> {
            isRunning = true;
            pbFirst.setVisibility(View.VISIBLE);
            pbSecond.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.GONE);
            bgThread.start();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBgThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}