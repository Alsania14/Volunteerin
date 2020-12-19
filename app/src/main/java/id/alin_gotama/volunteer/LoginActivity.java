package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

import id.alin_gotama.volunteer.Model.ServerLoginModel;
import id.alin_gotama.volunteer.Model.ServerRespon;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsernameLogin;
    private EditText etPasswordLogin;
    private Button btnSubmitLogin;
    private Button btnRegister;
    private ProgressBar pbLogin;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.etUsernameLogin = findViewById(R.id.etLognUsername);
        this.etPasswordLogin = findViewById(R.id.etLoginPassword);
        this.btnSubmitLogin = findViewById(R.id.btnLoginSumbit);
        this.btnRegister = findViewById(R.id.btnLoginRegister);
        this.pbLogin = findViewById(R.id.pbLogin);
        this.btnSubmitLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);
        
        sharedPreferences = getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);
        if(this.CheckLogin()){
            Intent HomeIntent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(HomeIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLoginSumbit){
            Services services = ApiClient.getRetrofit().create(Services.class);
            this.pbLogin.setVisibility(ProgressBar.VISIBLE);
            Call<ServerLoginModel> call = services.login(
                    this.etUsernameLogin.getText().toString().trim(),
                    this.etPasswordLogin.getText().toString().trim()
            );

            call.enqueue(new Callback<ServerLoginModel>() {
                @Override
                public void onResponse(Call<ServerLoginModel> call, Response<ServerLoginModel> response) {
                    if(response.body().getStatus().matches("200")){
                        pbLogin.setVisibility(ProgressBar.INVISIBLE);
                        editor = sharedPreferences.edit();
                        editor.putString(penyimpanan.VOLUNTEERIN_USERNAME,response.body().getUsername());
                        editor.putString(penyimpanan.VOLUNTEERIN_FULLNAME,response.body().getUserfullname());
                        editor.putString(penyimpanan.VOLUNTEERIN_ID,response.body().getUser_id());
                        editor.putString(penyimpanan.VOLUNTEERIN_NOMOR,response.body().getNo_telp());
                        editor.putString(penyimpanan.VOLUNTEERIN_BIO,response.body().getBio());
                        editor.apply();
                        Intent HomeIntent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(HomeIntent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, response.body().getErrors(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerLoginModel> call, Throwable t) {
                    pbLogin.setVisibility(ProgressBar.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "gagal " + t.toString() , Toast.LENGTH_SHORT).show();
                }

            });
        }

        if(v.getId() == R.id.btnLoginRegister){
            Intent register = new Intent(this,RegisterActivity.class);
            startActivity(register);
        }
    }

    private boolean CheckLogin(){
        String status = sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID,"");
        Log.d("penyimpanan",status);
        if(status.matches("")){
            Log.d("hasil","false");
            return false;
        }else{
            Log.d("hasil","true");
            return true;
        }
    }
}