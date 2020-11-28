package com.example.todoapp.Fragments.Notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteFragment extends BaseFragment {
    TextView editcontent;
    NotesModel note;
    Button saveEditing;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_note;
    }

    @Override
    public void initializeViews(View view) {
        editcontent = view.findViewById(R.id.editNote_et);
        getTheNote();


    }

    //===============================================
    private void getTheNote() {
        Bundle args = getArguments();

        if (args != null) {
            note = (NotesModel) args.getSerializable("Azza");//need to be  String
            editcontent.setText(note.getContent());
        }
    }

    //===================================================
    @Override
    public void setListeners() {
        saveEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTheDate();
               // Navigation.findNavController(v).navigate(R.id.action_editNoteFragment_to_stickyNotesFragment, budel);
            }
        });
    }

    //================================================
    private void getTheDate() {
        //get the date of the writeing task
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String dateTime = simpleDateFormat.format(calendar.getTime());
    }
}