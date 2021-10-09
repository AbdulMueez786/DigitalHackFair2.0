package com.example.digitalhackfair20.activity;

import android.app.Application;


public class MyApp extends Application {
    private static final String ONESIGNAL_APP_ID = "e5ed21c4-f5c1-4bdf-8f49-09d4950c97f8";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        //OneSignal Initialization
        //OneSignal.initWithContext(this);
        //OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
