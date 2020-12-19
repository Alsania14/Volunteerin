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

public class CustomAdapterAnggotaEvent extends RecyclerView.Adapter<CustomAdapterAnggotaEvent.viewHolder> {
    private Context context;
    private ArrayList<Anggota> anggotas;

    public CustomAdapterAnggotaEvent(Context context, ArrayList<Anggota> anggotas) {
        this.context = context;
        this.anggotas = anggotas;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.anggota_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvNama.setText(anggotas.get(position).getFull_name());
        holder.tvUsername.setText(anggotas.get(position).getUsername());
        holder.tvNomer.setText(anggotas.get(position).getNo_telp());
        holder.tvBio.setText(anggotas.get(position).getBio());
    }

    @Override
    public int getItemCount() {
        return anggotas.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView tvNama,tvUsername,tvNomer,tvBio;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvAnggotaRowNama);
            tvNomer = itemView.findViewById(R.id.tvAnggotaRowNoTelp);
            tvBio = itemView.findViewById(R.id.tvAnggotaRowBio);
            tvUsername = itemView.findViewById(R.id.tvAnggotaRowUsername);
        }
    }
}
