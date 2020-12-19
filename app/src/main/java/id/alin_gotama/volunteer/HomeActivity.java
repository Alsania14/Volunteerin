package id.alin_gotama.volunteer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.alin_gotama.volunteer.Model.DetailEvent;
import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import id.alin_gotama.volunteer.Fragment.MyEvent;
import id.alin_gotama.volunteer.Fragment.MySchedule;
import id.alin_gotama.volunteer.Fragment.Profile;
import id.alin_gotama.volunteer.Recycler.CustomAdapterMySchedule;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<Event> events;
    private SharedPreferences sharedPreferences;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE,Context.MODE_PRIVATE);
        this.user_id = sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"");
        this.setToken();
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
                else if(item.getItemId() == R.id.itemMenuMyEvent){
                    item.setChecked(true);
                    MyEvent();
                }
                else if(item.getItemId() == R.id.itemMenuSchedule){
                    item.setChecked(true);
                    MySchedule();
                }
                return false;
            }
        });



    }

    private void JoinEvent(){
        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ArrayList<Event>> call = services.readEvent(user_id);

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

                JoinEvent joinEvent = new JoinEvent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(JoinEvent.BUNDLE_EVENTS,events);
                joinEvent.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,joinEvent);
                ft.commit();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "PERIKSA KONEKSI ANDA !", Toast.LENGTH_SHORT).show();
                EventHelper eventHelper = new EventHelper(getBaseContext());
                eventHelper.open();
                events = eventHelper.query();
                JoinEvent joinEvent = new JoinEvent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(JoinEvent.BUNDLE_EVENTS,events);
                joinEvent.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,joinEvent);
                ft.commit();
            }
        });
    }

    private void CreateEvent(){
        CreateEvent createEvent = new CreateEvent();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,createEvent);
        ft.commit();
    }

    private void Profile(){
        Profile profile = new Profile(HomeActivity.this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,profile);
        ft.commit();
    }

    private void MyEvent(){
        final MyEvent myEvent = new MyEvent();
        final Bundle bundle = new Bundle();

        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ArrayList<Event>> call = services.readMyEvent(
                this.user_id
        );

        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                bundle.putParcelableArrayList(MyEvent.EVENTS,response.body());
                myEvent.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,myEvent);
                ft.commit();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "PERIKSA KONEKSI ANDA !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void MySchedule(){
        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ArrayList<DetailEvent>> call = services.myschedule(
                this.sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"")
        );

        call.enqueue(new Callback<ArrayList<DetailEvent>>() {
            @Override
            public void onResponse(Call<ArrayList<DetailEvent>> call, Response<ArrayList<DetailEvent>> response) {
                MySchedule mySchedule = new MySchedule();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(MySchedule.DETAIL_EVENTS,response.body());
                mySchedule.setArguments(bundle);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.HomeFrameLayout,mySchedule);
                ft.commit();
            }

            @Override
            public void onFailure(Call<ArrayList<DetailEvent>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "PERIKSA KONEKSI ANDA", Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    public void onClick(View v) {

    }

    public void setToken(){
        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ServerDefaultRespon> call = services.tokenFeeder(
                this.sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,""),
                this.sharedPreferences.getString(penyimpanan.VOLUNTEERIN_TOKEN,"")
        );

        call.enqueue(new Callback<ServerDefaultRespon>() {
            @Override
            public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                Toast.makeText(HomeActivity.this, "TOKEN TERUPDATE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}