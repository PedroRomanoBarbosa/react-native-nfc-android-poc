package com.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NFCReceiver extends BroadcastReceiver {
    public static final String TAG = "NFCReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "ON RECEIVE");
    }
}
