package com.example.bai3;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;

import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;


import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe;
    private Button btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private static final String AUTO_RESPONSE = "auto_response";


    private void findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response);
        llButtons = findViewById(R.id.ll_buttons);
        lvMessages = findViewById(R.id.lv_messages);
        btnSafe = findViewById(R.id.btn_safe);
        btnMayday = findViewById(R.id.btn_mayday);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initVariables();
        handleOnClickListener();
        Intent intent = getIntent();
        if (intent.getExtras() != null && intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY) != null) {
            processReceiveAddresses(intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY));
        }
    }

    private void respond(String to, String response) {
        reentrantLock.lock();
        try {
            requesters.remove(to);
            adapter.notifyDataSetChanged();
        } finally {
            reentrantLock.unlock();
        }
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(to, null, response, null, null);
    }

    private void respond(boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;
        ArrayList<String> requestersCopy = (ArrayList<String>) requesters.clone();
        for (String to : requestersCopy) {
            respond(to, outputString);
        }
    }

    public void processReceiveAddresses(ArrayList<String> addresses) {
        for (String address : addresses) {
            if (!requesters.contains(address)) {
                reentrantLock.lock();
                try {
                    requesters.add(address);
                    adapter.notifyDataSetChanged();
                } finally {
                    reentrantLock.unlock();
                }
            }
            if (swAutoResponse.isChecked()) respond(true);
        }
        Log.d("Requester", "requesters list size: " + requesters.size() + "\n");
        Log.d("Requester", "requesters list: " + requesters);
    }

    private void handleOnClickListener() {
        btnSafe.setOnClickListener(v -> respond(true));
        btnMayday.setOnClickListener(v -> respond(false));
        swAutoResponse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                llButtons.setVisibility(View.GONE);
            } else {
                llButtons.setVisibility(View.VISIBLE);
            }
            editor.putBoolean(AUTO_RESPONSE, isChecked).apply();
        });
    }

    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
                processReceiveAddresses(addresses);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppStatus.setRunning(true);
        if (broadcastReceiver == null) initBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppStatus.setRunning(false);
        unregisterReceiver(broadcastReceiver);
    }

    private void initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);
        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse) llButtons.setVisibility(View.GONE);
        initBroadcastReceiver();
    }

}

