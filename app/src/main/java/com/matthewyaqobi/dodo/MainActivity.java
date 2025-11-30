package com.matthewyaqobi.dodo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskDialog.TaskCallback,
        MainAdapter.TaskItemEventListener, BottomSheet.BottomSheetListener {
    private final MainAdapter adapter = new MainAdapter(this);
    private RecyclerView recyclerView;
    private TaskDialog dialog;
    private TaskDao taskDao;
    private List<Task> tasks;
    private static final String TAG = "MainActivity";
    private BottomSheet bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = AppDatabase.getAppDatabase(this).getTaskDao();
        recyclerView = findViewById(R.id.rv_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        tasks = taskDao.getTasks();
        adapter.addAllTasks(tasks);
        MaterialButton addTaskButton = findViewById(R.id.buttonAddTask_main);
        TextView todayDateTv = findViewById(R.id.tv_main_TodayDate);
        todayDateTv.setText(Utils.getTodayDate());
        ImageView guitarPick = findViewById(R.id.iv_main_guitarPick);

        guitarPick.setOnClickListener(onClick -> {
            Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
            startActivity(intent);
        });


        addTaskButton.setOnClickListener(onClick -> {
            dialog = new TaskDialog(false, null);
            dialog.show(getSupportFragmentManager(), null);
        });

    }

    @Override
    public void onNewTask(Task task) {
        long taskId = taskDao.addTask(task);
        if (taskId != 0) {
            task.setId((int) taskId);
            adapter.addNewTask(task);
            Log.i(TAG, "onNewTask: " + task);
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onUpdateTask(Task task) {
        Log.i(TAG, "onUpdateTask: " + task);
        taskDao.updateTask(task);
        adapter.updateTask(task);
    }

    @Override
    public void onItemClicked(Task task) {
        bottomSheet = new BottomSheet(task, this);
        bottomSheet.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onTaskDone(Task task) {
        taskDao.updateTask(task);
        adapter.updateTask(task);
    }

    @Override
    public void onTaskDelete(Task task) {
        taskDao.deleteTask(task);
        adapter.deleteTask(task);
    }
}