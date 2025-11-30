package com.matthewyaqobi.dodo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TaskDialog extends DialogFragment {
    private long selectedDate = 0;
    private int selectedTime = 0;
    private static final String TAG = "TaskDialog";
    private TaskCallback callback;
    private final Random random = new Random();
    private final List<String> myColors = Arrays.asList("#DBB59F", "#F4D28A", "#60C9B3");
    private final List<Integer> myStickers = Arrays.asList(R.drawable.plant, R.drawable.papaya, R.drawable.coffee_cup);
    private final boolean isEdit;
    private final Task task;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (TaskCallback) context;

    }

    @Override
    public void onStart() {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        super.onStart();
    }

    public TaskDialog(boolean isEdit, @Nullable Task task) {
        this.isEdit = isEdit;
        this.task = task;
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.add_edit_dialog, null, false);


        ExtendedFloatingActionButton timePickerFab = view.findViewById(R.id.fab_time_dialog);
        ExtendedFloatingActionButton datePickerFab = view.findViewById(R.id.fab_date_dialog);
        MaterialButton saveBtn = view.findViewById(R.id.btn_dialog_save);
        EditText etTitle = view.findViewById(R.id.et_dialog);
        TextView titleTv = view.findViewById(R.id.tv_title_dialog);

        if (isEdit && task != null) {
            titleTv.setText("Edit Task");
            etTitle.setText(task.getTaskTitle());

            if (task.getTime() > 0) {
                timePickerFab.setText(Utils.getFormattedTime(task.getTime()));
                selectedTime = task.getTime();
            }

            if (task.getDate() > 0) {
                datePickerFab.setText(Utils.getFormattedDate(task.getDate()));
                selectedDate = task.getDate();
            }
        }


        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(isEdit && task != null ? task.getTime() / 60 : 0)
                .setMinute(isEdit && task != null ? task.getTime() % 60 : 0)
                .setTitleText("Pick a time")
                .setTheme(R.style.CustomTimePickerTheme)
                .build();


        timePicker.addOnPositiveButtonClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String minuteStr = (minute < 10) ? "0" + minute : String.valueOf(minute);

            timePickerFab.setText(hour + ":" + minuteStr);

            selectedTime = getTimeInMinuet(hour, minute);
        });


        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(isEdit && task != null ? task.getDate() : MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.CustomDatePickerTheme)

                .build();

        datePicker.addOnPositiveButtonClickListener(aLong -> {
            datePickerFab.setText(Utils.getFormattedDate(datePicker.getSelection()));
            selectedDate = aLong;
        });


        timePickerFab.setOnClickListener(onClick -> timePicker.show(getChildFragmentManager(), null));

        datePickerFab.setOnClickListener(onClick -> datePicker.show(getChildFragmentManager(), null));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit && task != null && etTitle.length() > 0) {
                    task.setTaskTitle(etTitle.getText().toString());

                    task.setTime(selectedTime == 0 ? task.getTime() : selectedTime);
                    task.setDate(selectedDate == 0 ? task.getDate() : selectedDate);

                    callback.onUpdateTask(task);
                    selectedTime = 0;
                    selectedDate = 0;
                    dismiss();
                } else {
                    if (etTitle.length() > 0) {
                        Task task = new Task();
                        task.setTaskTitle(etTitle.getText().toString());
                        task.setDate(selectedDate);
                        task.setTime(selectedTime);
                        String randomBg = myColors.get(random.nextInt(myColors.size()));
                        int randomSticker = myStickers.get(random.nextInt(myStickers.size()));
                        task.setBgColorHex(randomBg);
                        task.setStickerRes(randomSticker);
                        callback.onNewTask(task);
                        Log.i(TAG, "onClick: " + task);
                        selectedTime = 0;
                        selectedDate = 0;
                        dismiss();
                    }
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }


    public static int getTimeInMinuet(int hour, int minuet) {
        return (hour * 60) + minuet;
    }

    public interface TaskCallback {
        void onNewTask(Task task);

        void onUpdateTask(Task task);
    }
}
