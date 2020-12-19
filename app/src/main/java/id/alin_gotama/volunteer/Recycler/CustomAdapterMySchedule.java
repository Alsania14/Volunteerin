package id.alin_gotama.volunteer.Recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.alin_gotama.volunteer.LihatAngggotaActivity;
import id.alin_gotama.volunteer.Model.DetailEvent;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.services.ApiClient;

public class CustomAdapterMySchedule extends RecyclerView.Adapter<CustomAdapterMySchedule.myViewHolder> {
    private Context context;
    public static ArrayList<DetailEvent> detailEvents;

    public CustomAdapterMySchedule(Context context, ArrayList<DetailEvent> paramDetailEvents) {
        this.context = context;
        detailEvents = new ArrayList<>();
        detailEvents = paramDetailEvents;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_row,parent,false);
        return new myViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {

        if(detailEvents.get(position).getStatus_member().matches("accept")){
            holder.tvTitle.setText(detailEvents.get(position).getEvent_name());
            holder.tvDescription.setText(detailEvents.get(position).getStatus_member());
            holder.cvMain.setAlpha((float) 1);
            StringBuilder url = new StringBuilder();
            url.append(ApiClient.BASE_URL);
            url.append("storage/EventImages/");
            url.append(detailEvents.get(position).getEvent_image());
            Log.d("gambar",url.toString());
            holder.cvMain.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            Glide.with(this.context).load(url.toString()).placeholder(R.drawable.logo).into(holder.ivCover);
            holder.cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LihatAngggotaActivity.class);
                    intent.putExtra(LihatAngggotaActivity.EVENT_ID,detailEvents.get(position).getEvent_id());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
        else if(detailEvents.get(position).getStatus_member().matches("pending")){
            holder.tvTitle.setText(detailEvents.get(position).getEvent_name());
            holder.tvDescription.setText(detailEvents.get(position).getStatus_member());
            holder.cvMain.setAlpha((float) 0.3);
            StringBuilder url = new StringBuilder();
            url.append(ApiClient.BASE_URL);
            url.append("storage/EventImages/");
            url.append(detailEvents.get(position).getEvent_image());
            Log.d("gambar",url.toString());
            Glide.with(this.context).load(url.toString()).placeholder(R.drawable.logo).into(holder.ivCover);
            holder.cvMain.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
            holder.cvMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "BELUM DITERIMA EVENT CREATOR", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return detailEvents.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,tvDescription;
        public ImageView ivCover;
        public CardView cvMain;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivCover = itemView.findViewById(R.id.ivMySchedule);
            this.tvTitle = itemView.findViewById(R.id.tvMyScheduleTitle);
            this.tvDescription = itemView.findViewById(R.id.tvMyScheduleDescription);
            this.cvMain = itemView.findViewById(R.id.cvSchedule);
        }
    }
}
