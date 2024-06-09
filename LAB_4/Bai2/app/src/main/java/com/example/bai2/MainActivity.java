package com.example.bai2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    private PowerState powerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initValue();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(
               powerState,
                intentFilter
        );

    }

    private void initValue() {
        powerState = new PowerState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(powerState);
    }

    private class PowerState extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null) return;
            if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
                Toast.makeText(
                        context,
                        context.getString(R.string.power_connected),
                        Toast.LENGTH_LONG
                ).show();
            }
            if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
                Toast.makeText(
                        context,
                        context.getString(R.string.power_disconnected),
                        Toast.LENGTH_LONG
                ).show();
            }
        }
    }
}

