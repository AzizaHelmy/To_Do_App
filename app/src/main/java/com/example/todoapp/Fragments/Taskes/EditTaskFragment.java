package com.example.todoapp.Fragments.Taskes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.example.todoapp.AsyncTask.UpdateAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditTaskFragment extends BaseFragment {
    EditText task2Edit;
    Button saveEditedTask;
    private TaskesModel taskesModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_task;
    }

    @Override
    public void initializeViews(View view) {
        task2Edit = view.findViewById(R.id.edit_task_et);
        saveEditedTask = view.findViewById(R.id.save_task_butt);
        getTask2Edit();
    }


    @Override
    public void setListeners() {
        saveEditedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = task2Edit.getText().toString();
                //get the date of the you update in it the task
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                String dateofUpdate = simpleDateFormat.format(calendar.getTime());
                taskesModel.setDate(dateofUpdate);
                taskesModel.setDetails(updatedTask);
                new UpdateAsyncTask(RoomFactory.getTaskessDb(getContext()).getTaskesDao()).execute(taskesModel);

                Navigation.findNavController(v).navigate(R.id.action_editTaskFragment_to_homeFragment);
            }
        });
    }

    //=========================================================
    private void getTask2Edit() {
        Bundle args = getArguments();

        if (args != null) {
            taskesModel = (TaskesModel) args.getSerializable("Task2Edit");
            task2Edit.setText(taskesModel.getDetails());
        }
    }
}