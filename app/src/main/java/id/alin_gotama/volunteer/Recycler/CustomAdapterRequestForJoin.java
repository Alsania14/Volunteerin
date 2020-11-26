package id.alin_gotama.volunteer.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.SQLModel.User;

public class CustomAdapterRequestForJoin extends RecyclerView.Adapter<CustomAdapterRequestForJoin.CustomViewHolder> {
    private Context context;
    private ArrayList<User> users;

    public CustomAdapterRequestForJoin(@NonNull Context context,ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requestforjoin_row,parent,false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvName.setText(users.get(position).getUsername());
        holder.tvUsername.setText(users.get(position).getUsername());
        holder.tvBio.setText(users.get(position).getBio());
        holder.tvNoTelp.setText(users.get(position).getNo_telp());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvUsername,tvBio,tvNoTelp;
        private Button btnAccept,btnRefuse;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvName = itemView.findViewById(R.id.tvRequestForJoinFullName);
            this.tvBio = itemView.findViewById(R.id.tvRequestForJoinBio);
            this.tvNoTelp = itemView.findViewById(R.id.tvRequestForJoinNoTelp);
            this.tvUsername = itemView.findViewById(R.id.tvRequestForJoinName);
            this.btnAccept = itemView.findViewById(R.id.btnRequestForJoinAccept);
            this.btnRefuse = itemView.findViewById(R.id.btnRequestForJoinDenied);
        }
    }
}
