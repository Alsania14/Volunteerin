package id.alin_gotama.volunteer.Recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.tvTitle.setText(detailEvents.get(position).getStatus_member());
        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_URL);
        url.append("storage/EventImages/");
        url.append(detailEvents.get(position).getEvent_image());
        Log.d("gambar",url.toString());
        Glide.with(this.context).load(url.toString()).placeholder(R.drawable.logo).into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        return detailEvents.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle,tvDescription;
        public ImageView ivCover;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivCover = itemView.findViewById(R.id.ivMySchedule);
            this.tvTitle = itemView.findViewById(R.id.tvMyScheduleTitle);
            this.tvDescription = itemView.findViewById(R.id.tvMyScheduleDescription);
        }
    }
}
