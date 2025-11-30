package com.matthewyaqobi.dodo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalenderActivity extends AppCompatActivity {
    private final CalenderAdapter adapter = new CalenderAdapter();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        TaskDao taskDao = AppDatabase.getAppDatabase(this).getTaskDao();
        List<Long> next30Days = Utils.get30Days();
        adapter.add30Days(next30Days);
        adapter.addAllTasks(taskDao.getTasks());

        RecyclerView recyclerView = findViewById(R.id.rv_calender);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        ImageView guitarPick = findViewById(R.id.guitar_pick_calender);
        guitarPick.setOnClickListener(onClick -> finish());
        TextView dateTv = findViewById(R.id.tv_time_calender);
        String startDate = Utils.getFormattedDate3(next30Days.get(0));
        String endDate = Utils.getFormattedDate3(next30Days.get(29));
        dateTv.setText(startDate + " - " + endDate);


    }
}