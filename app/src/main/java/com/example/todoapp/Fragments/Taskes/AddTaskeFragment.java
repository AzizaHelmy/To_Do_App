package com.example.todoapp.Fragments.Taskes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.todoapp.AsyncTask.InsertAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskeFragment extends BaseFragment {
    EditText newTasket;
    Button addTaskbutt;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_taske;
    }

    @Override
    public void initializeViews(View view) {
        newTasket = view.findViewById(R.id.add_task_et);
        addTaskbutt = view.findViewById(R.id.add_task_butt);
    }

    @Override
    public void setListeners() {
        addTaskbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task=newTasket.getText().toString();
                //get the date of the writeing task
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
                String dateTime=simpleDateFormat.format(calendar.getTime());
                Bundle bundle=new Bundle();
                bundle.putSerializable("Azza",dateTime);

                new InsertAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute(new TaskesModel(task,dateTime));
//

                Navigation.findNavController(v).navigate(R.id.action_addTaskeFragment_to_homeFragment,bundle);
            }
        });
    }
}