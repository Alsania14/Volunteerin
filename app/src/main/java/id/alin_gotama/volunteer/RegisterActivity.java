package id.alin_gotama.volunteer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.alin_gotama.volunteer.Model.ServerRegisterRespon;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etFullName;
    private EditText etUsername;
    private EditText etBio;
    private EditText etNomorTlp;
    private EditText etPassword;
    private EditText etPasswordCon;
    private Button btnCancel;
    private Button btnSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etRegisterFullName);
        etUsername = findViewById(R.id.etRegisterUsername);
        etBio = findViewById(R.id.etRegisterBio);
        etNomorTlp = findViewById(R.id.etRegisterNotelp);
        etPassword = findViewById(R.id.etRegisterPassword);
        etPasswordCon = findViewById(R.id.etRegisterPasswordCon);

        btnCancel = findViewById(R.id.btnRegisterCancel);
        btnSubmit = findViewById(R.id.btnRegisterSubmit);

        this.btnSubmit.setOnClickListener(this);
        this.btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegisterCancel){
            finish();
        }
        else if(v.getId() == R.id.btnRegisterSubmit){
            int err_status = 0;

            if(this.etFullName.getText().toString().matches("")){
                this.etFullName.setError("Masukkan Fullname");
                err_status = 1;
            }
            if(this.etUsername.getText().toString().matches("")){
                this.etUsername.setError("Masukkan Username");
                err_status = 1;
            }
            if (this.etBio.getText().toString().matches("")){
                this.etBio.setError("Masukkan Bio");
                err_status = 1;
            }
            if (this.etNomorTlp.getText().toString().matches("")){
                this.etNomorTlp.setError("Masukkan Nomor Telp");
                err_status = 1;
            }
            if(this.etPassword.getText().toString().matches("")){
                this.etPassword.setError("Masukkan Password");
                err_status = 1;
            }
            if(this.etPasswordCon.getText().toString().matches("")){
                this.etPasswordCon.setError("Masukkan Password Confirmation");
                err_status = 1;
            }

            if(err_status != 1){
                Toast.makeText(RegisterActivity.this, "TUNGGU", Toast.LENGTH_SHORT).show();
                Services services = ApiClient.getRetrofit().create(Services.class);
                Call<ServerRegisterRespon> call = services.register(
                        this.etFullName.getText().toString().trim(),
                        this.etUsername.getText().toString().trim(),
                        this.etNomorTlp.getText().toString().trim(),
                        this.etBio.getText().toString().trim(),
                        this.etPassword.getText().toString().trim()
                );

                call.enqueue(new Callback<ServerRegisterRespon>() {
                    @Override
                    public void onResponse(Call<ServerRegisterRespon> call, Response<ServerRegisterRespon> response) {
                        if(response.body().getStatus().matches("200")){
                            Toast.makeText(RegisterActivity.this, "Sukses", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },2000);
                        }else{
                            alert(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerRegisterRespon> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void alert(Response<ServerRegisterRespon> response){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("MESSAGE");

        StringBuilder message = new StringBuilder();

        if(response.body().getFull_name() != null){
            message.append(response.body().getFull_name());
            message.append("\n\n");
        }

        if(response.body().getUsername() != null){
            message.append(response.body().getUsername());
            message.append("\n\n");
        }

        if(response.body().getBio() != null){
            message.append(response.body().getBio());
            message.append("\n\n");
        }

        if(response.body().getNo_telp() != null){
            message.append(response.body().getNo_telp());
            message.append("\n\n");
        }

        if(response.body().getPassword() != null){
            message.append(response.body().getPassword());
            message.append("\n\n");
        }

        alertDialog.setMessage(message.toString())
                .setCancelable(true);
        alertDialog.create();
        alertDialog.show();
    }

}