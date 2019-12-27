package com.example.tktplproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HeroClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_class);
    }

    public void goToCreateDeckActivity(View view) {
        Intent intent = new Intent(this, CreateDeckActivity.class);
        startActivity(intent);
    }
}
