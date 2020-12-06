package id.alin_gotama.volunteer.Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import id.alin_gotama.volunteer.Model.ServerDefaultRespon;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.SQLModel.User;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.tvName.setText(String.valueOf(users.get(position).getId()));
        holder.tvUsername.setText(users.get(position).getUsername());
        holder.tvBio.setText(users.get(position).getBio());
        holder.tvNoTelp.setText(users.get(position).getNo_telp());

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("IZINKAN VOLUNTEER INI UNTUK BERGABUNG ? ");
                builder.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Services services = ApiClient.getRetrofit().create(Services.class);
                        Call<ServerDefaultRespon> call = services.acceptForJoin(
                                String.valueOf(users.get(position).getId())
                        );

                        call.enqueue(new Callback<ServerDefaultRespon>() {
                            @Override
                            public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                                    users.remove(position);
                                    notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                                Toast.makeText(context, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create();
                builder.show();
            }
        });

        holder.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("YAKIN AKAN MENGHAPUS VOLUNTEER INI ? ");
                builder.setPositiveButton("YES", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Services services = ApiClient.getRetrofit().create(Services.class);
                        Call<ServerDefaultRespon> call = services.deniedForJoin(
                                String.valueOf(users.get(position).getId())
                        );

                        call.enqueue(new Callback<ServerDefaultRespon>() {
                            @Override
                            public void onResponse(Call<ServerDefaultRespon> call, Response<ServerDefaultRespon> response) {
                                users.remove(position);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ServerDefaultRespon> call, Throwable t) {
                                Toast.makeText(context, "GAGAL", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create();
                builder.show();
            }
        });
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
