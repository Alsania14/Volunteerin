package id.alin_gotama.volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONObject;

import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;

import java.util.ArrayList;

import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Recycler.CustomAdapterMyEvent;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.SQLModel.RequestForJoinRespon;
import id.alin_gotama.volunteer.SQLModel.User;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EVENT = "event";
    public static final String POSITION = "position";
    public static CustomAdapterMyEvent customAdapterMyEvent;
    private SharedPreferences sharedPreferences;
    private int position;

    private Event event;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCreator;
    private TextView tvTglMulai;
    private TextView tvTglSelesai;
    private TextView tvMaxMember;

    private ImageView ivCoverImage;

    private Button btnReq,btnDelete,btnBack;
    private ProgressBar pbMyEventDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_detail);

        event = getIntent().getParcelableExtra(EVENT);
        Log.d("event",String.valueOf(event.getId()));
        position = getIntent().getIntExtra(POSITION,-1);

        this.tvTitle = findViewById(R.id.tvMyEventDetailTitle);
        this.tvDescription = findViewById(R.id.tvMyEventDetailDescription);
        this.tvCreator = findViewById(R.id.tvMyEventDetailCretor);
        this.tvTglMulai = findViewById(R.id.tvMyEventDetailTanggalMulai);
        this.tvTglSelesai = findViewById(R.id.tvMyEventDetailTanggalSelesai);
        this.tvMaxMember = findViewById(R.id.tvMyEventDetailMaxMember);

        this.ivCoverImage = findViewById(R.id.ivMyEventDetailCoverImage);

        this.btnReq = findViewById(R.id.btnMyEventDetailReq);
        this.btnReq.setOnClickListener(this);

        this.btnBack = findViewById(R.id.btnMyEventDetailBack);
        this.btnBack.setOnClickListener(this);

        this.btnDelete = findViewById(R.id.btnMyEventDetailDelete);
        this.btnDelete.setOnClickListener(this);

        this.pbMyEventDetail = findViewById(R.id.pbMyEventDetailEvent);

        tvTitle.setText(event.getNama());
        tvDescription.setText(event.getDeskripsi());
        tvCreator.setText(event.getCreator());
        tvTglMulai.setText(event.getTanggal_mulai());
        tvTglSelesai.setText(event.getTanggal_selesai());

        StringBuilder maxmember = new StringBuilder();
        maxmember.append(event.getMember());
        maxmember.append(" / ");
        maxmember.append(event.getMaximal_member());

        tvMaxMember.setText(maxmember.toString());

        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_URL);
        url.append("storage/EventImages/");
        url.append(event.getImage());

        Glide.with(MyEventDetailActivity.this).load(url.toString()).placeholder(R.drawable.logo).into(this.ivCoverImage);

        this.sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnMyEventDetailReq){
            Services services = ApiClient.getRetrofit().create(Services.class);
            Call<ArrayList<User>> call = services.requestForJoin(
                    String.valueOf(event.getId())
            );

            call.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    Intent intentan = new Intent(MyEventDetailActivity.this,RequestForJoinActivity.class);
                    intentan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentan.putExtra(RequestForJoinActivity.USERS,response.body());
                    startActivity(intentan);

                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Toast.makeText(MyEventDetailActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else if (v.getId() == R.id.btnMyEventDetailBack){
            finish();
        }
        else if(v.getId() == R.id.btnMyEventDetailDelete){
            Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(MyEventDetailActivity.this);
            dialog.setTitle("YAKIN INGIN MENGHAPUS EVENT INI ?");
            dialog.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pbMyEventDetail.setVisibility(ProgressBar.VISIBLE);

                    Services services = ApiClient.getRetrofit().create(Services.class);
                    Call<ServerDefaultRespon> call = services.deleteEvent(
                            String.valueOf(event.getId())
                    );

                    call.enqueue(new Callback<ServerDefaultRespon>() {
                        @Override
                        public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                            customAdapterMyEvent.remove(position);
                            customAdapterMyEvent.notifyDataSetChanged();
                            pbMyEventDetail.setVisibility(ProgressBar.INVISIBLE);
                            Toast.makeText(MyEventDetailActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                            Toast.makeText(MyEventDetailActivity.this, "PERIKSA KONEKSI ANDA !", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });

            dialog.setCancelable(true);
            dialog.setNegativeButton("NO", new AlertDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MyEventDetailActivity.this, "CANCEL", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.create();
            dialog.show();
        }
    }
}