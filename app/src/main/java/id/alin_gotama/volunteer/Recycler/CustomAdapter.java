package id.alin_gotama.volunteer.Recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.alin_gotama.volunteer.JoinEventActivity;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.services.ApiClient;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private Context context;
    private ArrayList<Event> events;

    public CustomAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    public void remove(int i){
        this.events.remove(i);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);

        return new CustomViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.tvTitle.setText(events.get(position).getNama());
        holder.tvDescription.setText(events.get(position).getDeskripsi());

        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_URL);
        url.append("storage/EventImages/");
        url.append(events.get(position).getImage());

        Log.d("url",url.toString());
        Glide.with(context).load(url.toString()).placeholder(R.drawable.logo)
                .into(holder.ivEvent);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenJoinEvent = new Intent(context, JoinEventActivity.class);
                intenJoinEvent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intenJoinEvent.putExtra(JoinEventActivity.EVENT_POSITION,position);
                intenJoinEvent.putExtra(JoinEventActivity.EVENT_KEY,events.get(position));
                JoinEventActivity.customAdapter = CustomAdapter.this;
                context.startActivity(intenJoinEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private CardView cardView;
        private ImageView ivEvent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardView = itemView.findViewById(R.id.CardViewRecyclerView);
            this.ivEvent = itemView.findViewById(R.id.ivImageRecyclerView);
            this.tvTitle = itemView.findViewById(R.id.tvTitleRecyclerView);
            this.tvDescription = itemView.findViewById(R.id.tvDescriptionRecyclerView);
        }
    }
}
