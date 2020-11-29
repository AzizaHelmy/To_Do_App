package com.example.todoapp.Fragments.Taskes;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;


public class TaskDetailsFragment extends BaseFragment {
    TextView taskDetails;
    TextView taskDate;
    CheckBox checkBox;
    private TaskesModel taskesModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_details;
    }

    @Override
    public void initializeViews(View view) {
        taskDetails = view.findViewById(R.id.task_tv);
        taskDate = view.findViewById(R.id.date_tv);
        checkBox = view.findViewById(R.id.done_cb);
        getTaskDetails();

    }

    //===========================================================
    private void getTaskDetails() {
        Bundle args = getArguments();

        if (args != null) {
            taskesModel = (TaskesModel) args.getSerializable("sTask");
            taskDetails.setText(taskesModel.getDetails());
            taskDate.setText(taskesModel.getDate());

        }
    }

    //==============================================================
    @Override
    public void setListeners() {

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               //check it's state first befor doing cross out
                if (!taskDetails.getPaint().isStrikeThruText()) {
                    taskDetails.setPaintFlags(taskDetails.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                   taskDetails.setPaintFlags(taskDetails.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

            }
        });
    }
}