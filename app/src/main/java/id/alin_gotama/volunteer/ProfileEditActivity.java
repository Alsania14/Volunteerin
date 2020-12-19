package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import id.alin_gotama.volunteer.Fragment.Profile;
import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEditActivity extends AppCompatActivity {
    private TextView tvFullName,tvBio,tvNomor,tvUsername;
    private Button btnSave;
    private ProgressBar pbEdit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tvFullName = findViewById(R.id.etProfileEditFullName);
        tvUsername = findViewById(R.id.etProfileEditUsername);
        tvNomor = findViewById(R.id.etProfileEditNomorHP);
        tvBio = findViewById(R.id.etProfileEditBio);

        tvFullName.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_FULLNAME,"Unknown"));
        tvUsername.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_USERNAME,"Unknown"));
        tvBio.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_BIO,"Unknown"));
        tvNomor.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_NOMOR,"Unknown"));

        btnSave = findViewById(R.id.btnProfileEditSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbEdit.setVisibility(ProgressBar.VISIBLE);
                Services services = ApiClient.getRetrofit().create(Services.class);
                Call<ServerDefaultRespon> call = services.updateUser(
                        sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,""),
                        sharedPreferences.getString(penyimpanan.VOLUNTEERIN_FULLNAME,""),
                        sharedPreferences.getString(penyimpanan.VOLUNTEERIN_USERNAME,""),
                        sharedPreferences.getString(penyimpanan.VOLUNTEERIN_BIO,""),
                        sharedPreferences.getString(penyimpanan.VOLUNTEERIN_NOMOR,"")
                );

                call.enqueue(new Callback<ServerDefaultRespon>() {
                    @Override
                    public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                        editor.remove(penyimpanan.VOLUNTEERIN_FULLNAME);
                        editor.remove(penyimpanan.VOLUNTEERIN_BIO);
                        editor.remove(penyimpanan.VOLUNTEERIN_NOMOR);
                        editor.remove(penyimpanan.VOLUNTEERIN_USERNAME);
                        editor.apply();

                        editor.putString(penyimpanan.VOLUNTEERIN_FULLNAME,tvFullName.getText().toString());
                        editor.putString(penyimpanan.VOLUNTEERIN_USERNAME,tvUsername.getText().toString());
                        editor.putString(penyimpanan.VOLUNTEERIN_NOMOR,tvNomor.getText().toString());
                        editor.putString(penyimpanan.VOLUNTEERIN_BIO,tvBio.getText().toString());
                        editor.apply();

                        Profile.tvFullName.setText(tvFullName.getText().toString());
                        Profile.tvUsername.setText(tvUsername.getText().toString());
                        Profile.tvNoTlp.setText(tvNomor.getText().toString());
                        Profile.tvBio.setText(tvBio.getText().toString());

                        finish();
                    }

                    @Override
                    public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                        Toast.makeText(ProfileEditActivity.this, "PERIKSA KONEKSI ANDA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        pbEdit = findViewById(R.id.pbProfileEdit);
    }
}