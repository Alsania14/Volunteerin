package id.alin_gotama.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.alin_gotama.volunteer.Recycler.CustomAdapterRequestForJoin;
import id.alin_gotama.volunteer.SQLModel.User;

public class RequestForJoinActivity extends AppCompatActivity {
    public static final String USERS = "USERS";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_join);
        users = getIntent().getParcelableArrayListExtra(USERS);
        this.recyclerView = findViewById(R.id.rvRequestForJoin);
        this.layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        CustomAdapterRequestForJoin customAdapterRequestForJoin  = new CustomAdapterRequestForJoin(this,users);
        this.recyclerView.setAdapter(customAdapterRequestForJoin);
        Toast.makeText(this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();

    }

}