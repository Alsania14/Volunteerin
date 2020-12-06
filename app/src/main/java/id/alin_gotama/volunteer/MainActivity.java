package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import id.alin_gotama.volunteer.DatabaseHelper.EventHelper;
import id.alin_gotama.volunteer.services.ApiClient;

public class MainActivity extends AppCompatActivity {
    private static final int WAKTU  = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventHelper eventHelper = new EventHelper(this);
        eventHelper.open();
        eventHelper.close();

        FirebaseMessaging.getInstance().subscribeToTopic("all").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Connected To Server", Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Home = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(Home);
                finish();
            }
        },WAKTU);
    }
}