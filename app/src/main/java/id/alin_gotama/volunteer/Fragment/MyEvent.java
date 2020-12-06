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
import id.alin_gotama.volunteer.Recycler.CustomAdapterMyEvent;
import id.alin_gotama.volunteer.SQLModel.Event;

public class MyEvent extends Fragment {
    public static final String EVENTS = "myEvents";
    private Context context;
    private ArrayList<Event> events;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public MyEvent() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myevent,container,false);
        Bundle bundle = getArguments();
        this.events = bundle.getParcelableArrayList(EVENTS);

        this.context = getActivity().getBaseContext();
        CustomAdapterMyEvent customAdapter = new CustomAdapterMyEvent(events,context);

        this.recyclerView = view.findViewById(R.id.rvMyEvent);
        this.layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(customAdapter);

        return view;
    }
}
