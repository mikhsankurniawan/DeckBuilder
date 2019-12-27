package com.example.tktplproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateDeckActivity extends AppCompatActivity {
    AppViewModel viewModel;
    List<Card> cardLists;
    List<Deck> deckLists;
    String CHANNEL_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        cardLists = viewModel.getAllCards().getValue();
        setContentView(R.layout.activity_create_deck);
        RecyclerView recyclerView = findViewById(R.id.create_deck_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AppAdapter(this, cardLists));

        String className = getIntent().getStringExtra("class_name");

        Button createDeck = (Button) findViewById(R.id.create_deck_button);
        TextView totalCards = (TextView) findViewById(R.id.total_temporary_text);
        EditText deckNameEditText = (EditText) findViewById(R.id.deck_name_edit);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.plus_button)
                .setContentTitle("Hearthstone Deck Builder")
                .setContentText("Deck Created")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Context context = this;

        requestPermission();

        createDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(totalCards.getText().toString()) != 30) {
                    Context context = getApplicationContext();
                    CharSequence text = "Deck must contain exact 30 cards.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (deckNameEditText.getText().toString().isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Deck name must not be null.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    try {
                        getApplicationContext().startService(new Intent(getApplicationContext(), CreateDeckService.class));
                        viewModel.addDeck(deckNameEditText.getText().toString(), className);
                        getApplicationContext().stopService(new Intent(getApplicationContext(), CreateDeckService.class));
                        new CountDownTimer(5000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                createDeck.setText("seconds remaining: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                createDeck.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(context, OpenGLES20Activity.class);
                                startActivity(intent);
                                if (isNetworkConnected()) {
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                                    notificationManager.notify(NotificationID.getID(), builder.build());
                                }
                            }
                        }.start();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void requestPermission() {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateDeckActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            // Permission has already been granted
            CharSequence text = "Already Granted.";
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        Context c = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CharSequence text = "Access Granted.";
                    Toast toast = Toast.makeText(c, text, duration);
                    toast.show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    CharSequence text = "Access Denied.";
                    Toast toast = Toast.makeText(c, text, duration);
                    toast.show();
                }
                return;
            }
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void onCardSelected() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.activity_create_deck, CardDetailsFragment.newInstance(), "cardDetails")
                .commit();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    @Override
//    public void onBackPressed() {
//        String identifier = getIntent().getStringExtra("identifier");
//        Bundle bundle = new Bundle();
//        bundle.putString("identifier", identifier);
//        Fragment fragment = HeroDetailsFragment.newInstance();
//        fragment.setArguments(bundle);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack(null)
//                .add(R.id.activity_create_deck, fragment, "heroDetails")
//                .commit();
//    }
}