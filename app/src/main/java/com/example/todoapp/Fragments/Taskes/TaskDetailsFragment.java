package com.example.todoapp.Fragments.Taskes;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.todoapp.AsyncTask.UpdateAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;


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
            checkBox.setChecked( taskesModel.isCkecked());
           

        }
    }

    //==============================================================
    @Override
    public void setListeners() {

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    taskesModel.setCkecked(true);
                    checkBox.setChecked(true);
                    taskDetails.setPaintFlags(taskDetails.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    taskesModel.setCrossOut(true);
                    new UpdateAsyncTask(RoomFactory.getTaskessDb(getContext()).getTaskesDao()).execute(taskesModel);
                }else{
                    taskesModel.setCkecked(false);
                    checkBox.setChecked(false);
                    taskDetails.setPaintFlags(taskDetails.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    taskesModel.setCrossOut(false);
                    new UpdateAsyncTask(RoomFactory.getTaskessDb(getContext()).getTaskesDao()).execute(taskesModel);
                }


            }
        });
    }
}