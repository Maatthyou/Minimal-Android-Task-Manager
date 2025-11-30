package com.matthewyaqobi.dodo;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.CalenderViewHolder> {
    private final List<Long> next30Days = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    private static final String TAG = "CalenderAdapter";


    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalenderViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_calender, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position) {
        holder.bind(next30Days.get(position));
    }

    @Override
    public int getItemCount() {
        return next30Days.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void add30Days(List<Long> next30Days) {
        this.next30Days.addAll(next30Days);
        notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAllTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public class CalenderViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        TextView tasksTv;

        public CalenderViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.tv_day_itemCalender);
            tasksTv = itemView.findViewById(R.id.tv_tasks_itemCalender);
        }

        public void bind(Long timeStamp) {
            dateTv.setText(Utils.getFormattedDate2(timeStamp));

            Log.i(TAG, "bind: " + timeStamp);

            StringBuilder stringBuilder = new StringBuilder();
            for (Task task : tasks) {
                Log.i(TAG, "bind: " + task.getDate());
                if (task.getDate() == timeStamp&& !task.getCompleted()) {
                    stringBuilder.append(task.getTaskTitle()).append("\n");


                }
                tasksTv.setText(stringBuilder.toString().trim());
            }
        }
    }
}

