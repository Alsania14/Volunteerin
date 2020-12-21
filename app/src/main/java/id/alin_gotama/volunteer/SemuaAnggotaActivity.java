package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import id.alin_gotama.volunteer.Recycler.CustomAdapterSemuaAnggota;
import id.alin_gotama.volunteer.SQLModel.Anggota;

public class SemuaAnggotaActivity extends AppCompatActivity {
    public static final String ANGGOTAS = "ANGGOTAS";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Anggota> anggotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_anggota);
        anggotas = getIntent().getParcelableArrayListExtra(ANGGOTAS);

        CustomAdapterSemuaAnggota customAdapterSemuaAnggota = new CustomAdapterSemuaAnggota(anggotas,this);

        recyclerView = findViewById(R.id.rvSemuaAnggotaContent);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapterSemuaAnggota);
    }
}