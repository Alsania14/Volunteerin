package id.alin_gotama.volunteer.Recycler;

import android.content.Context;
import android.content.Intent;
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

import id.alin_gotama.volunteer.MyEventDetailActivity;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.SQLModel.Event;
import id.alin_gotama.volunteer.services.ApiClient;

public class CustomAdapterMyEvent extends RecyclerView.Adapter<CustomAdapterMyEvent.CustomViewHolder> {
    public static ArrayList<Event> events;
    private Context context;

    public CustomAdapterMyEvent(ArrayList<Event> events, Context context) {
        CustomAdapterMyEvent.events = events;
        this.context = context;
    }

    public void remove(int position){
        events.remove(position);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myevent_row_rv,parent,false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_URL);
        url.append("storage/EventImages/");
        url.append(events.get(position).getImage());

        Glide.with(context).load(url.toString()).placeholder(R.drawable.logo).into(holder.ivCoverImage);
        holder.tvTitle.setText(events.get(position).getNama());
        holder.tvDescription.setText(events.get(position).getDeskripsi());

        holder.cvMyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyEventDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(MyEventDetailActivity.POSITION,position);
                intent.putExtra(MyEventDetailActivity.EVENT,events.get(position));
                MyEventDetailActivity.customAdapterMyEvent = CustomAdapterMyEvent.this;
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private ImageView ivCoverImage;
        private CardView cvMyEvent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cvMyEvent = itemView.findViewById(R.id.cvCardViewData);
            this.tvTitle = itemView.findViewById(R.id.tvMyEventTitle);
            this.tvDescription = itemView.findViewById(R.id.tvMyEventDescription);
            this.ivCoverImage = itemView.findViewById(R.id.ivMyEventImageView);
        }
    }
}
