package com.example.todoapp.Fragments.Notes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.navigation.Navigation;

import com.example.todoapp.Async2.InsertAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.Models.TaskesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotesFragment extends BaseFragment {

    EditText titileNote, contentNote;
    Button addNote;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_notes;
    }

    @Override
    public void initializeViews(View view) {
        titileNote = view.findViewById(R.id.note_titel_et);
        contentNote = view.findViewById(R.id.note_content_et);
        addNote = view.findViewById(R.id.add_note_butt);
    }

    @Override
    public void setListeners() {
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tNote=titileNote.getText().toString();
                String cNote=contentNote.getText().toString();

                //get the date of the writeing task
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
                String dateTime=simpleDateFormat.format(calendar.getTime());
                Bundle bundle=new Bundle();
                bundle.putString("Azza",dateTime);

               new InsertAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute(new NotesModel(tNote,cNote,dateTime));
//

                Navigation.findNavController(v).navigate(R.id.action_addNotesFragment_to_stickyNotesFragment,bundle);
            }
        });
    }
}