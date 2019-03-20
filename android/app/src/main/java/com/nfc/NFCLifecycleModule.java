package com.nfc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;

import javax.annotation.Nonnull;

import static com.nfc.MainActivity.NFC_LISTENER_READY;
import static com.nfc.MainActivity.TAG;

public class NFCLifecycleModule extends ReactContextBaseJavaModule {
    public NFCLifecycleModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        LocalBroadcastManager.getInstance(reactContext).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Log.v(getName(), "ON RECEIVE: " + tagFromIntent.toString());
                sendEvent("NfcManagerDiscoverTag", Arguments.createMap());
            }
        },
        new IntentFilter("android.nfc.action.TECH_DISCOVERED"));
    }

    @Nonnull
    @Override
    public String getName() {
        return "NFCLifecycleModule";
    }

    @ReactMethod
    public void nfcListenerReady() {
        getReactApplicationContext().sendBroadcast(new Intent(NFC_LISTENER_READY));
        Log.v(TAG, "NFC LISTENER READY");
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(RCTNativeAppEventEmitter.class).emit(eventName, params);
    }
}
