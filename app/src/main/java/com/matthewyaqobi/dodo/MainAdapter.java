package com.matthewyaqobi.dodo;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<Task> tasks = new ArrayList<>();
    private final TaskItemEventListener listener;

    public MainAdapter(TaskItemEventListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAllTasks(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void addNewTask(Task task) {
        tasks.add(0, task);
        notifyItemInserted(0);
    }

    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId() == tasks.get(i).getId()) {
                tasks.set(i, task);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void deleteTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (task.getId() == tasks.get(i).getId()) {
                tasks.remove(i);
                notifyItemRemoved(i);
                break;

            }
        }
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from
                (parent.getContext()).inflate(R.layout.item_rv_main, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView stickerIv;
        TextView timeTv;
        TextView dateTv;
        TextView taskTitleTv;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.consL_item_main);
            stickerIv = itemView.findViewById(R.id.iv_sticker_item);
            timeTv = itemView.findViewById(R.id.tv_taskTime_item_rv_main);
            dateTv = itemView.findViewById(R.id.tv_taskDate_itemRv_main);
            taskTitleTv = itemView.findViewById(R.id.tv_taskTitle_item_rv_main);
        }

        @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        @SuppressLint("SetTextI18n")
        public void bindTask(Task task) {


            String formatedDate = Utils.getFormattedDate(task.getDate());

            taskTitleTv.setText(task.getTaskTitle());
            constraintLayout.setBackgroundColor(Color.parseColor(task.getBgColorHex()));
            stickerIv.setImageResource(task.getStickerRes());


            if (task.getTime() > 0) {
                timeTv.setText(Utils.getFormattedTime(task.getTime()));
            } else {
                timeTv.setText("");
            }
            if (task.getDate() > 0) dateTv.setText(formatedDate);
            else dateTv.setText("");

            itemView.setOnClickListener(onClick -> {
                listener.onItemClicked(task);
            });


        }

    }

    public interface TaskItemEventListener {
        void onItemClicked(Task task);


    }
}
