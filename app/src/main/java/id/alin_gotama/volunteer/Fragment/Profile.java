package id.alin_gotama.volunteer.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.alin_gotama.volunteer.GlobalObjectScope.GlobalScope;
import id.alin_gotama.volunteer.HomeActivity;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import id.alin_gotama.volunteer.ProfileEditActivity;
import id.alin_gotama.volunteer.R;

public class Profile extends Fragment {
    @SuppressLint("StaticFieldLeak")
    public static TextView tvUsername;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvFullName;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvNoTlp;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvBio;

    private Button btnLogoout;
    private Button btnEdit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Profile() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        sharedPreferences =  getContext().getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE,Context.MODE_PRIVATE);

        tvFullName = view.findViewById(R.id.tvProfileFullName);
        tvUsername = view.findViewById(R.id.tvProfileUsername);
        tvBio = view.findViewById(R.id.tvProfileBio);
        tvNoTlp = view.findViewById(R.id.tvProfileNotelp);

        this.btnLogoout = view.findViewById(R.id.btnProfileLogout);
        this.btnEdit = view.findViewById(R.id.btnProfileEdit);

        tvUsername.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_USERNAME,"Empty"));
        tvFullName.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_FULLNAME,"Empty"));
        tvBio.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_BIO,"Empty"));
        tvNoTlp.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_NOMOR,"Empty"));
        btnLogoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        return  view;
    }

    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Log Out Sekarang ?");
        builder.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "BATAL LOG OUT", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor = sharedPreferences.edit();
                editor.remove(penyimpanan.VOLUNTEERIN_ID);
                editor.remove(penyimpanan.VOLUNTEERIN_USERNAME);
                editor.remove(penyimpanan.VOLUNTEERIN_FULLNAME);
                boolean clear = editor.commit();
                if(clear){
                    System.exit(0);
                }
            }
        });

        builder.create();
        builder.show();
    }

    private void edit(){
        Intent intent = new Intent(getContext(), ProfileEditActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
