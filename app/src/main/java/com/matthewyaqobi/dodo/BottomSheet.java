package com.matthewyaqobi.dodo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class BottomSheet extends BottomSheetDialogFragment {
    private final Task task;
    private BottomSheetListener listener;
    private static final String TAG = "BottomSheet";


    public BottomSheet(Task task, BottomSheetListener listener) {
        this.task = task;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        MaterialButton editBtn = view.findViewById(R.id.editBtn_bottomSheet);
        MaterialButton doneBtn = view.findViewById(R.id.doneBtn_bottomSheet);
        MaterialButton deleteBtn = view.findViewById(R.id.deleteBtn_bottomSheet);

        doneBtn.setOnClickListener(onClick -> {
            task.setCompleted(true);
            task.setBgColorHex("#9E9E9E");
            task.setStickerRes(R.drawable.check_24px);
            listener.onTaskDone(task);
            dismiss();

        });

        deleteBtn.setOnClickListener(onClick -> {
            listener.onTaskDelete(task);
            dismiss();
        });

        editBtn.setOnClickListener(onClick -> {
            TaskDialog dialog = new TaskDialog(true, task);
            dialog.show(getChildFragmentManager(), null);
            Log.i(TAG, "onCreateView: "+task);


        });

        return view;
    }

    @Override
    public int getTheme() {
        return R.style.CustomSheetTheme;
    }

    public interface BottomSheetListener {
        void onTaskDone(Task task);

        void onTaskDelete(Task task);
    }

}

