package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import id.alin_gotama.volunteer.DatabaseHelper.DatabaseHelper;
import id.alin_gotama.volunteer.DatabaseHelper.EventHelper;
import id.alin_gotama.volunteer.Fragment.DatePickerFragment;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.Service.Service;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEvent extends AppCompatActivity implements View.OnClickListener {
    public static final String EVENT = "EVENT";
    public static final String RESULT = "RESULT";
    private EditText etNama,etDeskripsi,etMaximalMember;
    private TextView tvTanggalMulai,tvTanggalSelesai;
    private Button btnSubmit;
    private RadioGroup rgUpdate;

    private RadioButton rbOpen,rbClose,rbGoing,rbDone;

    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);
        event = getIntent().getParcelableExtra(EVENT);
        this.etNama = findViewById(R.id.etUpdateEventName);
        this.etNama.setText(event.getNama());

        this.etDeskripsi = findViewById(R.id.etUpdateEventDescription);
        this.etDeskripsi.setText(event.getDeskripsi());

        this.etMaximalMember = findViewById(R.id.etUpdateEventMaxMember);
        this.etMaximalMember.setText(String.valueOf(event.getMaximal_member()));

        this.tvTanggalMulai = findViewById(R.id.tvUpdateEventTanggalMulai);
        this.tvTanggalMulai.setText(event.getTanggal_mulai());

        this.tvTanggalSelesai = findViewById(R.id.tvUpdateEventTanggalSelesai);
        this.tvTanggalSelesai.setText(event.getTanggal_selesai());

        this.tvTanggalSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment(UpdateEvent.this,tvTanggalSelesai);
                FragmentManager fragmentManager = getSupportFragmentManager();
                datePickerFragment.show(fragmentManager,DatePickerFragment.class.getSimpleName());
            }
        });
        this.tvTanggalMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment(UpdateEvent.this,tvTanggalMulai);
                FragmentManager fragmentManager = getSupportFragmentManager();
                datePickerFragment.show(fragmentManager,DatePickerFragment.class.getSimpleName());
            }
        });

        this.rgUpdate = findViewById(R.id.rgUpdateEvent);

        this.rbClose = findViewById(R.id.rbUpdateEVentClose);
        this.rbOpen = findViewById(R.id.rbUpdateEVentOpen);
        this.rbGoing = findViewById(R.id.rbUpdateEVentOnGoing);
        this.rbDone = findViewById(R.id.rbUpdateEVentDone);

        if(this.event.getStatus().matches("open")){
            rbOpen.setChecked(true);
        }
        else if(event.getStatus().matches("close")){
            rbClose.setChecked(true);
        }
        else if(event.getStatus().matches("ongoing")){
            rbGoing.setChecked(true);
        }
        else if(event.getStatus().matches("done")){
            rbDone.setChecked(true);
        }

        this.etMaximalMember.setText(String.valueOf(event.getMaximal_member()));

        this.btnSubmit = findViewById(R.id.btnUpdateEventSubmit);

        this.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnUpdateEventSubmit){
//            VALIDASI
            StringBuilder error = new StringBuilder();
            error.append("");
            if(etNama.getText().toString().matches("")){
                error.append("Nama Event Tidak Boleh Kosong");
            }

            if(etDeskripsi.getText().toString().matches("")){
                error.append("Deskripsi Tidak Boleh Kosong");
            }

            if(etMaximalMember.getText().toString().matches("")){
                error.append("Maximal Member Tidak Boleh Kosong");
            }

            if(tvTanggalMulai.getText().toString().matches("YYYY-MM-DD")){
                error.append("Tentukan Tanggal Mulai");
            }

            if(tvTanggalSelesai.getText().toString().matches("YYYY-MM-DD")){
                error.append("Tentukan Tanggal Selesai");
            }
            String status = "";
            if(rgUpdate.getCheckedRadioButtonId() == -1){
                error.append("Pilih Status Event");
            }else{
                if(rgUpdate.getCheckedRadioButtonId() == R.id.rbUpdateEVentOpen){
                    status = "open";
                }
                else if(rgUpdate.getCheckedRadioButtonId() == R.id.rbUpdateEVentClose){
                    status = "close";
                }
                else if(rgUpdate.getCheckedRadioButtonId() == R.id.rbUpdateEVentDone){
                    status = "done";
                }
                else if(rgUpdate.getCheckedRadioButtonId() == R.id.rbUpdateEVentOnGoing){
                    status = "ongoing";
                }
            }

            if(error.toString().matches("")){
                Services services = ApiClient.getRetrofit().create(Services.class);
                Call<ServerDefaultRespon> call = services.updateevent(
                        etNama.getText().toString(),
                        etDeskripsi.getText().toString(),
                        tvTanggalMulai.getText().toString(),
                        tvTanggalSelesai.getText().toString(),
                        status,
                        etMaximalMember.getText().toString(),
                        String.valueOf(event.getId())
                );

                event.setNama(etNama.getText().toString());
                event.setDeskripsi(etDeskripsi.getText().toString());
                event.setTanggal_mulai(tvTanggalMulai.getText().toString());
                event.setTanggal_selesai(tvTanggalSelesai.getText().toString());
                event.setStatus(status);
                event.setMaximal_member(Integer.parseInt(etMaximalMember.getText().toString()));

                call.enqueue(new Callback<ServerDefaultRespon>() {
                    @Override
                    public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                        Toast.makeText(UpdateEvent.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra(RESULT,event);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                        Toast.makeText(UpdateEvent.this, "GAGAL" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}