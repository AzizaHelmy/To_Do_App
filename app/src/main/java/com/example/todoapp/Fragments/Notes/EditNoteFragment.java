package com.example.todoapp.Fragments.Notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.todoapp.Async2.UpdateAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteFragment extends BaseFragment {
    TextView content2Edit;
    TextView tilil2edit;
    NotesModel note;
    Button saveEditing;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_edit_note;
    }

    @Override
    public void initializeViews(View view) {
        content2Edit = view.findViewById(R.id.note_content2edit_et);
        tilil2edit = view.findViewById(R.id.note_titel2edit_et);
        saveEditing=view.findViewById(R.id.update_note_butt);
        getTheNote2Edit();


    }

    //===============================================
    private void getTheNote2Edit() {
        Bundle args = getArguments();

        if (args != null) {
            note = (NotesModel) args.getSerializable("Azza");//need to be  String
            content2Edit.setText(note.getContent());
            tilil2edit.setText(note.getTitile());
        }
    }

    //===================================================
    @Override
    public void setListeners() {
        saveEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatedtitle = tilil2edit.getText().toString();
                String updatedcontent = content2Edit.getText().toString();
                //get the date of the you update in it the task
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                String dateofUpdate = simpleDateFormat.format(calendar.getTime());
                note.setDate(dateofUpdate);
                note.setTitile(updatedtitle);
                note.setContent(updatedcontent);
               new UpdateAsyncTask(RoomFactory.getTaskessDb(getContext()).getTaskesDao()).execute(note);

                Navigation.findNavController(v).navigate(R.id.action_editNoteFragment_to_stickyNotesFragment);
            }
        });
    }

    //================================================

}