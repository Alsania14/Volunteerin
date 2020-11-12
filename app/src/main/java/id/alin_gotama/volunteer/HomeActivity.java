package id.alin_gotama.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.function.Consumer;

import id.alin_gotama.volunteer.DatabaseHelper.DatabaseHelper;
import id.alin_gotama.volunteer.DatabaseHelper.EventHelper;
import id.alin_gotama.volunteer.Fragment.CreateEvent;
import id.alin_gotama.volunteer.Fragment.JoinEvent;
import id.alin_gotama.volunteer.Fragment.Profile;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.JoinEvent();

        this.bottomNavigationView = findViewById(R.id.HomeNavBar);
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.itemMenuJoinEvent){
                    item.setChecked(true);
                    JoinEvent();
                }
                else if(item.getItemId() == R.id.itemMenuCreateEvent){
                    item.setChecked(true);
                    CreateEvent();
                }
                else if(item.getItemId() == R.id.itemMenuProfile){
                    item.setChecked(true);
                    Profile();
                }
                return false;
            }
        });



    }

    private void JoinEvent(){
        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ArrayList<Event>> call = services.readEvent();

        call.enqueue(new Callback<ArrayList<Event>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {

                events = response.body();
                final EventHelper eventHelper = new EventHelper(getBaseContext());
                eventHelper.open();
                eventHelper.truncate();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        events.forEach(new Consumer<Event>() {
                            @Override
                            public void accept(Event e) {
                                long a = eventHelper.insert(e);
                                Log.d("image",e.getImage());
                            }
                        });

                        eventHelper.close();
                    }
                });

                JoinEvent joinEvent = new JoinEvent(HomeActivity.this,events);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,joinEvent);
                ft.commit();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "PERIKSA KONEKSI ANDA !", Toast.LENGTH_SHORT).show();
                EventHelper eventHelper = new EventHelper(getBaseContext());
                ArrayList<Event> events;
                eventHelper.open();
                events = eventHelper.query();

                JoinEvent joinEvent = new JoinEvent(HomeActivity.this,events);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,joinEvent);
                ft.commit();
                eventHelper.close();

            }
        });
    }

    private void CreateEvent(){
        CreateEvent createEvent = new CreateEvent(HomeActivity.this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,createEvent);
        ft.commit();
    }

    private void Profile(){
        Profile profile = new Profile(HomeActivity.this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,profile);
        ft.commit();
    }

    @Override
    public void onClick(View v) {

    }
}