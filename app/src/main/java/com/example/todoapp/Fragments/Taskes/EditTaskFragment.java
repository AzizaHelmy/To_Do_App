package com.example.todoapp.Fragments.Taskes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.example.todoapp.BaseFragment;
import com.example.todoapp.R;

public class EditTaskFragment extends BaseFragment {
    EditText task2Edit;
    Button saveEditedTask;

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
                Navigation.findNavController(v).navigate(R.id.action_editTaskFragment_to_homeFragment);
            }
        });
    }

    //=========================================================
    private void getTask2Edit() {
        Bundle args = getArguments();

        if (args != null) {
            String task = args.getString("Task2Edit");
            task2Edit.setText(task);
        }
    }
}