package id.alin_gotama.volunteer.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.Recycler.CustomAdapter;
import id.alin_gotama.volunteer.SQLModel.Event;

public class JoinEvent extends Fragment {
    private Context context;
    private ArrayList<Event> events;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public JoinEvent(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = new ArrayList<>();
        this.events = events;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        CustomAdapter customAdapter = new CustomAdapter(context,events);
        recyclerView = view.findViewById(R.id.HomeRecyclerView);

        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customAdapter);

        return view;
    }
}
