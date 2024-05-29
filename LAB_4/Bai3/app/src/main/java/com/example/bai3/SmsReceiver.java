package com.example.bai3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.Locale;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            String queryString = context.getString(R.string.are_you_ok).toLowerCase(Locale.getDefault());
            android.os.Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                String format = bundle.getString("format");
                for (int i = 0; i < pdus.length; i++) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                }
                ArrayList<String> addresses = new ArrayList<>();
                for (SmsMessage msg : messages) {
                    if (msg.getMessageBody().toLowerCase(Locale.getDefault()).contains(queryString)) {
                        addresses.add(msg.getOriginatingAddress());
                    }
                }
                if (!addresses.isEmpty()) {
                    if (AppStatus.isRunning()) {
                        Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                        iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.sendBroadcast(iForwardBroadcastReceiver);
                    } else {
                        Intent intentStartActivity = new Intent(context, MainActivity.class);
                        intentStartActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intentStartActivity.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                        context.startActivity(intentStartActivity);
                    }
                }
            }
        }
    }
}
