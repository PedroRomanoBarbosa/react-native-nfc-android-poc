package com.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.react.ReactActivity;

public class MainActivity extends ReactActivity {
    public static final String TAG = "MainActivity";
    public static final String NFC_LISTENER_READY = "NFC_LISTENER_READY";
    private Intent nfcIntent;
    BroadcastReceiver receiver;

    @Override
    protected String getMainComponentName() {
        return "nfc";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Log.v(TAG, "ACTION: " + intent.getAction());
        if ("android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) {
            nfcIntent = intent;
        }
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(TAG, "ON RECEIVE: " + intent.getAction());
                if (nfcIntent != null) {
                    Log.v(TAG, "SENDING: " + nfcIntent.getAction());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(nfcIntent);
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(NFC_LISTENER_READY));
    }
}
