package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import id.alin_gotama.volunteer.Recycler.CustomAdapter;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinEventActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EVENT_KEY = "event_key";
    public static final String EVENT_POSITION = "event_position";

    private TextView tvTitle,tvDescription,tvEventCreator;
    private TextView tvEventTanggalMulai,tvEventTanggalSelesai;
    private TextView tvMaximalMember;
    private ProgressBar progressBar;

    private Button btnJoinNow;
    private Button btnCancel;
    private Event event;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ImageView ivCoverImage;
    private int position;
    public static CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);

        this.event = getIntent().getParcelableExtra(EVENT_KEY);
        this.position = getIntent().getIntExtra(EVENT_POSITION,-1);
        this.tvTitle = findViewById(R.id.tvJoinEventTitle);
        this.tvDescription = findViewById(R.id.tvJointEventDeskripsi);
        this.tvEventTanggalMulai = findViewById(R.id.tvJoinEventTanggalMulai);
        this.tvEventTanggalSelesai = findViewById(R.id.tvJoinEventTanggalSelesai);
        this.tvMaximalMember = findViewById(R.id.tvJoinEventMaximalMember);
        this.tvEventCreator = findViewById(R.id.tvJoinEventEventCreator);
        this.progressBar = findViewById(R.id.pbJoinEventProgressBar);

        this.btnJoinNow = findViewById(R.id.btnJoinEventJoinNow);
        this.btnCancel = findViewById(R.id.btnJoinEventCancel);

        this.btnJoinNow.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);

        this.sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);

        this.tvTitle.setText(event.getNama());
        this.tvDescription.setText(event.getDeskripsi());
        this.tvEventCreator.setText(event.getCreator());
        this.tvEventTanggalMulai.setText(event.getTanggal_mulai());
        this.tvEventTanggalSelesai.setText(event.getTanggal_selesai());
        StringBuilder member = new StringBuilder();
        member.append(event.getMember());
        member.append(" / ");
        member.append(event.getMaximal_member());

        this.tvMaximalMember.setText(member.toString());

        this.ivCoverImage = findViewById(R.id.ivJoinEventCoverImage);

        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_URL);
        url.append("storage/EventImages/");
        url.append(event.getImage());

        Glide.with(this).load(url.toString()).placeholder(R.drawable.logo)
                .into(this.ivCoverImage);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnJoinEventJoinNow){
            this.progressBar.setVisibility(ProgressBar.VISIBLE);
            String user_id = this.sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"");
            final Services services = ApiClient.getRetrofit().create(Services.class);
            Call<ServerDefaultRespon> call = services.joinEvent(
                    String.valueOf(this.event.getId()),
                    user_id
            );

            call.enqueue(new Callback<ServerDefaultRespon>() {
                @Override
                public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    String status = response.body().getStatus();
                    if(response.body().getStatus().matches("200")){
                        Toast.makeText(JoinEventActivity.this, "BERHASIL DAFTAR", Toast.LENGTH_SHORT).show();
                        JoinEventActivity.customAdapter.remove(position);
                        JoinEventActivity.customAdapter.notifyDataSetChanged();
                        finish();
                    }
                    else{
                        Toast.makeText(JoinEventActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                    }
                    Log.d("status",status);
                }

                @Override
                public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(JoinEventActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        if(v.getId() == R.id.btnJoinEventCancel){
            finish();
        }
    }
}