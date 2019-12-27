package com.example.tktplproject;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.room.Room;

public class HearthstoneApp extends Application {
    AppDatabase db;
    private static HearthstoneApp mInstance;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler timerHandler;
    int Seconds, Minutes, MilliSeconds;
    TextView timerTextView;
    boolean isTimerRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "hs-database").fallbackToDestructiveMigration().build();
        timerHandler = new Handler();
        timerTextView = new TextView(this);
        isTimerRunning = false;
    }

    public static synchronized HearthstoneApp getInstance() {
        return mInstance;
    }

    public AppDatabase getDatabase() {
        return db;
    }
}
