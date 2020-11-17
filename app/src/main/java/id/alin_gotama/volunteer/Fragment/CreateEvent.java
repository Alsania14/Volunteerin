package id.alin_gotama.volunteer.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.InputStream;

import id.alin_gotama.volunteer.HomeActivity;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateEvent extends Fragment implements View.OnClickListener,DatePickerFragment.onSelect{
    public static final String TANGGAL_MULAI = "tanggal_mulai";
    public static final String TANGGAL_SELESAI = "tanggal_selesai";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_IMAGE_REQUEST = 1;
    private int imageStatus = 0;

    private Context context;

    private EditText etNama;
    private EditText etDeskripsi;
    private TextView tvTanggalMulai;
    private TextView tvTanggalSelesai;
    private TextView tvStatus;
    private TextView tvCoverImage;
    private EditText etMaxMember;
    private RadioGroup rgStatus;

    private Button btnSumbit;
    private ProgressBar progressBar;
    private Button btnImage;

    private ImageView ivCoverimage;

    private File imageFile;

    private SharedPreferences sharedPreferences;

    private String status = "";

    DatePickerFragment.onSelect monSelect;

    public CreateEvent() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.context = view.getContext();
        this.sharedPreferences = this.context.getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE,Context.MODE_PRIVATE);

        this.etNama = view.findViewById(R.id.etCreateEventName);
        this.etDeskripsi = view.findViewById(R.id.etCreateEventDescription);
        this.tvTanggalMulai = view.findViewById(R.id.tvCreateEventTanggalMulai);
        this.tvTanggalMulai.setOnClickListener(this);
        this.tvTanggalSelesai = view.findViewById(R.id.tvCreateEventTanggalSelesai);
        this.tvTanggalSelesai.setOnClickListener(this);
        this.etMaxMember = view.findViewById(R.id.etCreateEventMaxMember);
        this.tvStatus = view.findViewById(R.id.tvCreateEventStatus);
        this.tvCoverImage =view.findViewById(R.id.tvCreateEventCoverImage);

        this.ivCoverimage = view.findViewById(R.id.ivCreateEventCoverimage);

        this.btnSumbit = view.findViewById(R.id.btnCreateEventSubmit);
        this.btnSumbit.setOnClickListener(this);

        this.progressBar = view.findViewById(R.id.pbCreateEventSubmitLoading);

        this.btnImage = view.findViewById(R.id.btnCreateEventImage);
        this.btnImage.setOnClickListener(this);

        this.rgStatus = view.findViewById(R.id.rgCreateEvent);

        this.rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbCreateEVentOpen){
                    Toast.makeText(context, "EVENT ANDA TERBUKA UNTUK SEMUA VOLUNTEER", Toast.LENGTH_LONG).show();
                    status = "open";
                }

                if(checkedId == R.id.rbCreateEVentClose){
                    Toast.makeText(context, "EVENT ANDA TERTUTUP", Toast.LENGTH_LONG).show();
                    status = "close";
                }

                if(checkedId == R.id.rbCreateEVentOnGoing){
                    Toast.makeText(context, "EVENT ANDA SUDAH BERJALAN TIDAK DAPAT MENERIMA VOLUNTEER", Toast.LENGTH_LONG).show();
                    status = "ongoing";
                }

                if(checkedId == R.id.rbCreateEVentDone){
                    Toast.makeText(context, "EVENT ANDA DINYATAKAN TELAH SELESAI, HANYA SEBAGAI DOKUMENTASI", Toast.LENGTH_SHORT).show();
                    status = "done";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvCreateEventTanggalMulai){
            DatePickerFragment datePickerFragment = new DatePickerFragment(this.context,this.tvTanggalMulai);
            FragmentManager mFragmentManager = getChildFragmentManager();
            datePickerFragment.show(mFragmentManager,DatePickerFragment.class.getSimpleName());
        }

        if(v.getId() == R.id.tvCreateEventTanggalSelesai){
            DatePickerFragment datePickerFragment = new DatePickerFragment(this.context,this.tvTanggalSelesai);
            FragmentManager mFragmentManager = getChildFragmentManager();
            datePickerFragment.show(mFragmentManager,DatePickerFragment.class.getSimpleName());
        }

        if(v.getId() == R.id.btnCreateEventSubmit) {
            int validation = 0;

            if (this.etNama.getText().toString().matches("") || this.etNama.getText() == null) {
                validation += 1;
                this.etNama.setError("Mohon di isi");
            }
            if (this.etDeskripsi.getText().toString().matches("") || this.etDeskripsi.getText() == null) {
                validation += 1;
                this.etDeskripsi.setError("Mohon di isi");
            }
            if (this.tvTanggalMulai.getText().toString().matches("YYYY-MM-DD") || this.tvTanggalMulai.getText() == null) {
                validation += 1;
                this.tvTanggalMulai.setError("Mohon di isi");
            }
            if (this.tvTanggalSelesai.getText().toString().matches("YYYY-MM-DD") || this.tvTanggalSelesai.getText() == null) {
                validation += 1;
                this.tvTanggalSelesai.setError("Mohon di isi");
            }

            if (this.status.matches("")) {
                validation += 1;
                this.tvStatus.setError("Mohon Di pilih");
            }

            if(this.etMaxMember.getText().toString().matches("") || this.etMaxMember.getText() == null){
                validation += 1;
                this.etMaxMember.setError("Mohon di isi");
            }

            if(this.imageStatus == 0){
                validation += 1;
                this.tvCoverImage.setError("Mohon Pilih Gambar");
            }

            if(validation == 0){

                String SharedPref_user_id;
                SharedPref_user_id = sharedPreferences.getString(penyimpanan.VOLUNTEERIN_ID, "");

                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

                MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

                RequestBody user_id =
                        RequestBody.create(MediaType.parse("multipart/form-data"), SharedPref_user_id);

                RequestBody nama =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.etNama.getText().toString().trim());

                RequestBody description =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.etDeskripsi.getText().toString().trim());

                RequestBody tangga_mulai =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.tvTanggalMulai.getText().toString().trim());

                RequestBody tanggal_selesai =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.tvTanggalSelesai.getText().toString().trim());

                RequestBody status =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.status);

                RequestBody maximal_member =
                        RequestBody.create(MediaType.parse("multipart/form-data"), this.etMaxMember.getText().toString().trim());

                Services services = ApiClient.getRetrofit().create(Services.class);
                Call<ServerDefaultRespon> call = services.createEvent(
                        user_id,
                        nama,
                        description,
                        tangga_mulai,
                        tanggal_selesai,
                        status,
                        maximal_member,
                        body
                );
                this.progressBar.setVisibility(ProgressBar.VISIBLE);

                call.enqueue(new Callback<ServerDefaultRespon>() {
                    @Override
                    public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                        Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
            }

        }

        if(v.getId() == R.id.btnCreateEventImage){
            Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
            fileintent.setType("image/*");
            try{
                startActivityForResult(fileintent, 1);
            }catch (ActivityNotFoundException e){
                Log.d("error","some error");
            }
        }
    }

    @Override
    public void onSelectDate(String text) {
        this.tvTanggalMulai.setText(text);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MY_IMAGE_REQUEST){

                Log.d("result",String.valueOf(resultCode));
                Uri selectedImage = data.getData();

                String result = requestRead(selectedImage);
                imageFile = new File(result);

                if(imageFile.exists()){
                    this.ivCoverimage.setImageURI(selectedImage);
                    this.imageStatus = 1;
                }else{
                    Toast.makeText(context, "TIDAK DAPAT MENGGUNAKAN GAMBAR TERSEBUT !", Toast.LENGTH_LONG).show();
                }

        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public String getPathFromURI(Uri uri){
        String realPath="";
    // SDK < API11
        if (Build.VERSION.SDK_INT < 11) {
            String[] proj = { MediaStore.Images.Media.DATA };
            @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = 0;
            String result="";
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPath=cursor.getString(column_index);
            }
        }
        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19){
            String[] proj = { MediaStore.Images.Media.DATA };
            CursorLoader cursorLoader = new CursorLoader(context, uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if(cursor != null){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                realPath = cursor.getString(column_index);
            }
        }
        // SDK > 19 (Android 4.4)
        else{
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            Log.d("colon",wholeID);
            String id = wholeID;
            if(wholeID.contains(":")){
                id = wholeID.split(":")[1];
            }
            String[] column = { MediaStore.Images.Media.DATA };
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{ id }, null);
            int columnIndex = 0;
            if (cursor != null) {
                columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    realPath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        }
        return realPath;
    }

    public String requestRead(Uri selectedImage) {
        String result ="tai";
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((HomeActivity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            result = getPathFromURI(selectedImage);
        }
        return result;
    }
}
