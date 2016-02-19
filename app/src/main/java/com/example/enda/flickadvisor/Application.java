package com.example.enda.flickadvisor;

import com.facebook.stetho.Stetho;

/**
 * Created by enda on 19/02/16.
 */
public class Application extends android.app.Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
