package com.example.bai3;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class SlowTask extends AsyncTask<String, Long, Void> {
    private ProgressDialog pdWaiting;
    private Long startTime;
    private Context context;
    private TextView tvStatus;

    public SlowTask(Context context, TextView tvStatus) {
        this.context = context;
        this.tvStatus = tvStatus;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // We can use UI thread
        pdWaiting = new ProgressDialog(context);
        startTime = System.currentTimeMillis();
        tvStatus.setText("Start time: " + startTime);
        pdWaiting.setMessage(context.getString(R.string.please_wait));
        pdWaiting.show();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);

        // We can use UI thread here
        if (pdWaiting.isShowing()) {
            pdWaiting.dismiss();
        }

        // Show done messages
        tvStatus.append("\nEnd time: " + System.currentTimeMillis());
        tvStatus.append("\nDone!");
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        tvStatus.append("\nWorking..." + values[0]);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            for (long i = 0; i < 3; i++) {
                Thread.sleep(2000);
                publishProgress(i);
            }
        } catch (Exception e) {
            Log.e("SlowJob", e.getMessage());
        }
        return null;
    }
}
