package com.example.todoapp.Fragments.Taskes;

import android.view.View;

import com.example.todoapp.AsyncTask.GetAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;


public class TaskDetailsFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_task_details;
    }

    @Override
    public void initializeViews(View view) {
       // getTask();
    }

    private void getTask() {
        new GetAsyncTask(RoomFactory.getTaskessDb(getContext()).getTaskesDao()).execute();
    }

    @Override
    public void setListeners() {

    }
}