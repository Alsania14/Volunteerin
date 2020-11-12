package id.alin_gotama.volunteer.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.alin_gotama.volunteer.HomeActivity;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import id.alin_gotama.volunteer.R;

public class Profile extends Fragment {
    private TextView tvUsername;
    private TextView tvFullName;
    private TextView tvNoTlp;
    private TextView tvBio;
    private Context context;
    private Button btnLogoout;
    private Button btnEdit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Profile(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        sharedPreferences =  context.getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE,Context.MODE_PRIVATE);

        this.tvFullName = view.findViewById(R.id.tvProfileFullName);
        this.tvUsername = view.findViewById(R.id.tvProfileUsername);
        this.tvBio = view.findViewById(R.id.tvProfileBio);
        this.tvNoTlp = view.findViewById(R.id.tvProfileNotelp);

        this.btnLogoout = view.findViewById(R.id.btnProfileLogout);
        this.btnEdit = view.findViewById(R.id.btnProfileLogout);

        this.tvUsername.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_USERNAME,"Empty"));
        this.tvFullName.setText(sharedPreferences.getString(penyimpanan.VOLUNTEERIN_FULLNAME,"Empty"));

        this.btnLogoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert();
            }
        });

        return  view;
    }

    private void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Log Out Sekarang ?");
        builder.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "BATAL LOG OUT", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor = sharedPreferences.edit();
                editor.clear();
                boolean clear = editor.commit();
                if(clear){
                    System.exit(0);
                }

            }
        });

        builder.create();
        builder.show();

    }
}
