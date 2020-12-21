package id.alin_gotama.volunteer.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.SQLModel.Anggota;

public class CustomAdapterSemuaAnggota extends RecyclerView.Adapter<CustomAdapterSemuaAnggota.viewHolder> {
    private ArrayList<Anggota> anggotas;
    private Context context;

    public CustomAdapterSemuaAnggota(ArrayList<Anggota> anggotas, Context context) {
        this.anggotas = anggotas;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.semuaanggota_row,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvNama.setText(anggotas.get(position).getFull_name());
        holder.tvUsername.setText(anggotas.get(position).getUsername());
        holder.tvNomor.setText(anggotas.get(position).getNo_telp());
        holder.tvBio.setText(anggotas.get(position).getBio());
    }

    @Override
    public int getItemCount() {
        return anggotas.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama,tvUsername,tvNomor,tvBio;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvSemuaAnggotaNama);
            tvUsername = itemView.findViewById(R.id.tvSemuaAnggotaUsername);
            tvNomor = itemView.findViewById(R.id.tvSemuaAnggotaNotelp);
            tvBio = itemView.findViewById(R.id.tvSemuaAnggotaBio);

        }
    }
}
