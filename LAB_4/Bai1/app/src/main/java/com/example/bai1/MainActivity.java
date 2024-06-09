package com.example.bai1;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver = null;
    private IntentFilter intentFilter = null;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private void checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS
            }, PERMISSION_REQUEST_CODE);
        }
    }

    private void processReceive(Context context, Intent intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_message), Toast.LENGTH_LONG).show();
        TextView tvContent = findViewById(R.id.tv_content);
        String SMS_EXTRA = "pdus";
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] messages = (Object[]) bundle.get(SMS_EXTRA);
            if (messages != null) {
                StringBuilder sms = new StringBuilder();
                for (Object message : messages) {
                    SmsMessage smsMsg;
                    if (Build.VERSION.SDK_INT >= 23) {
                        String format = bundle.getString("format");
                        smsMsg = SmsMessage.createFromPdu((byte[]) message, format);
                    } else {
                        smsMsg = SmsMessage.createFromPdu((byte[]) message);
                    }
                    String smsBody = smsMsg.getMessageBody();
                    String address = smsMsg.getDisplayOriginatingAddress();
                    sms.append(address).append(":\n").append(smsBody).append("\n");
                }
                tvContent.setText(sms.toString());
            } else {
                Log.e("EX1", "No SMS messages received.");
            }
        } else {
            Log.e("EX1", "No bundle received.");
        }
    }

    private void initBroadcastReceiver() {
        intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions();
        initBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            initBroadcastReceiver();
        }
        registerReceiver(broadcastReceiver, intentFilter); // Đăng ký broadcast
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver); //Hủy đăng ký broadcast
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied, notify the user
                Toast.makeText(this, "SMS permission denied. Cannot receive messages.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

