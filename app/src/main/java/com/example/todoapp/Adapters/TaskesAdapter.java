package com.example.todoapp.Adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.ReminderBroadcast;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TaskesAdapter extends RecyclerView.Adapter<TaskesAdapter.AllTaskesViewHolder> {

    List<TaskesModel> taskesList;
    OnViewClick viewClicked;
    onBoxClick boxClicked;
    Context context;

    public interface OnViewClick {
        void onSwitchClick(View view, int position);
    }

    public interface onBoxClick {
        void onCheackBokClicked();
    }

    public TaskesAdapter(List<TaskesModel> taskesList, OnViewClick viewClicked, onBoxClick boxClicked, Context context) {
        this.taskesList = taskesList;
        this.viewClicked = viewClicked;
        this.boxClicked = boxClicked;
        this.context = context;
    }
//=================================================================
    @NonNull
    @Override
    public AllTaskesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskes_item_rv, parent, false);
        return new AllTaskesViewHolder(view);
    }
//=================================================================
    @Override
    public void onBindViewHolder(@NonNull AllTaskesViewHolder holder, int position) {
        TaskesModel model = taskesList.get(position);
        holder.details.setText(model.getDetails());
        holder.date.setText(model.getDate());
        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossOutTheTaske(holder);

            }
        });
        holder.remender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // viewClicked.onSwitchClick(v, position);
                showTimePickerDialog(holder);

            }
        });
    }
//=====================================================================
    @Override
    public int getItemCount() {
        return taskesList.size();
    }
//======================================================================
    private void showTimePickerDialog( AllTaskesViewHolder holder) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hourOfDay, minute);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
                String dateTime = simpleDateFormat.format(calendar.getTime());
                Bundle bundle = new Bundle();
                bundle.putSerializable("Azza", dateTime);
            }
        }, 12, 0, false);
        // timePickerDialog.setC
        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             holder.remender.setChecked(false);
            }
        });
        //display previous selected time
        // timePickerDialog.updateTime();
        timePickerDialog.show();
    }

  //=======================================================================
    private void crossOutTheTaske(@NonNull AllTaskesViewHolder holder) {
        if(!holder.details.getPaint().isStrikeThruText()){
            holder.details.setPaintFlags(holder.details.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            holder.details.setPaintFlags(holder.details.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

 //===========================*ViewHolder*=======================================
    class AllTaskesViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView details;
        TextView date;
        MaterialCheckBox done;
        SwitchMaterial remender;

        public AllTaskesViewHolder(@NonNull View itemView) {
            super(itemView);
            // title = itemView.findViewById(R.id.title_tv);
            details = itemView.findViewById(R.id.task_tv);
            date = itemView.findViewById(R.id.date_tv);
            done = itemView.findViewById(R.id.done_cb);
            remender = itemView.findViewById(R.id.remender_sw);

        }
    }
}
