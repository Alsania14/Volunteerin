package id.alin_gotama.volunteer.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.alin_gotama.volunteer.Model.DetailEvent;
import id.alin_gotama.volunteer.R;
import id.alin_gotama.volunteer.Recycler.CustomAdapterMySchedule;
import id.alin_gotama.volunteer.Service.Service;
import id.alin_gotama.volunteer.services.ApiClient;
import id.alin_gotama.volunteer.services.Services;
import retrofit2.Call;
import id.alin_gotama.volunteer.Penyimpanan.penyimpanan;
import retrofit2.Callback;
import retrofit2.Response;

public class MySchedule extends Fragment {
    public static final String DETAIL_EVENTS = "DETAIL_EVENTS";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private ArrayList<DetailEvent> detailEvents;

    public MySchedule() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.myschedule,container,false);
        this.recyclerView = view.findViewById(R.id.rvSchedule);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.sharedPreferences = getContext().getSharedPreferences(penyimpanan.VOLUNTEERIN_STORAGE, Context.MODE_PRIVATE);
        detailEvents = new ArrayList<>();
        this.detailEvents = getArguments().getParcelableArrayList(DETAIL_EVENTS);
        CustomAdapterMySchedule customAdapterMySchedule = new CustomAdapterMySchedule(getContext(),detailEvents);
        recyclerView.setAdapter(customAdapterMySchedule);

        return view;
    }
}
