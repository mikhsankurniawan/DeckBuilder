package com.example.tktplproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class CreateDeckService extends Service {
    public CreateDeckService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CharSequence text = "Adding new deck to deck list.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
