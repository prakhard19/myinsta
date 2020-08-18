package com.example.myinstaclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("u3OSOaWWaXPk3an7N1xcb3SztVHzHXtFFXjPoc1X")
                .clientKey("egFCts6VHCyaiWd931mEapC7cGl6IQEVbf9JoR4J")
                .server("https://parseapi.back4app.com/")
                .build()
        );

    }
}