package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import id.alin_gotama.volunteer.Recycler.CustomAdapterAnggotaEvent;
import id.alin_gotama.volunteer.SQLModel.Anggota;
import id.alin_gotama.volunteer.Service.Service;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;


public class LihatAngggotaActivity extends AppCompatActivity {
    public static final String EVENT_ID = "EVENT_ID";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Anggota> anggotas;
    private String event_id;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_angggota);
        this.sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);

        this.recyclerView = findViewById(R.id.rvLihatAnggota);
        event_id = getIntent().getStringExtra(EVENT_ID);

        Log.d("event_idnya",String.valueOf(event_id));

        Services services = ApiClient.getRetrofit().create(Services.class);
        Call<ArrayList<Anggota>> call = services.ambilSemuaAnggota(this.event_id,sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"-1"));

        call.enqueue(new Callback<ArrayList<Anggota>>() {
            @Override
            public void onResponse(Call<ArrayList<Anggota>> call, Response<ArrayList<Anggota>> response) {
                CustomAdapterAnggotaEvent customAdapterAnggotaEvent = new CustomAdapterAnggotaEvent(getBaseContext(),response.body());
                layoutManager = new LinearLayoutManager(getBaseContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(customAdapterAnggotaEvent);

                Toast.makeText(LihatAngggotaActivity.this, "SUKSES "+sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"ga")+ " " + event_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Anggota>> call, Throwable t) {
                Toast.makeText(LihatAngggotaActivity.this, "GAGAL "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}