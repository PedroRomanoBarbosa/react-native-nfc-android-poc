package com.nfc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NFCDiscovery extends Service {
    public static final String TAG = "NFCDiscovery";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "ON START COMMAND");
        return super.onStartCommand(intent, flags, startId);
    }
}
