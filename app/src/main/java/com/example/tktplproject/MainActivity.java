package com.example.tktplproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver appReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-db").build();

        AppViewModel appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        if (appViewModel.getAllClasses() == null) {
            try {
                appViewModel.populateClassDatabase();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_main, HeroClassListFragment.newInstance(), "heroClassList")
                    .commit();
        }

        appReceiver = new AppReceiver();
    }

    public void onHeroSelected() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.activity_main, HeroDetailsFragment.newInstance(), "heroDetails")
                .commit();
    }

    public void goToHeroClassActivity(View view) {
        Intent intent = new Intent(this, HeroClassActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        registerReceiver(appReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(appReceiver);
        super.onStop();
    }
}
