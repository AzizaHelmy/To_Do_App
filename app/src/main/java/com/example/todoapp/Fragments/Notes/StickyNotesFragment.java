package com.example.todoapp.Fragments.Notes;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.todoapp.Adapters.NotesAdapter;
import com.example.todoapp.Async2.DeleteAsyncTask;
import com.example.todoapp.Async2.GetAsyncTask;
import com.example.todoapp.BaseFragment;
import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class StickyNotesFragment extends BaseFragment {
    RecyclerView notesRv;
    NotesAdapter notesAdapter;
    List<NotesModel> notesList = new ArrayList<>();
    FloatingActionButton fabNote;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sticky_notes;
    }

    @Override
    public void initializeViews(View view) {
        fabNote = view.findViewById(R.id.note_fab);
        notesRv = view.findViewById(R.id.notes_rv);
        getAllNotes();
        checkForNotes();
        setUpRecyclerView(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
              Navigation.findNavController(getView()).navigate(R.id.homeFragment);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void setListeners() {
        fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_stickyNotesFragment_to_addNotesFragment);
            }
        });
    }

    //==============================================
    private void setUpRecyclerView(View v) {
        //notesRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        notesRv.setLayoutManager(layoutManager);
        //layoutManager.setStackFromTop(true);
        //layoutManager.setReverseLayout(true);
        notesAdapter = new NotesAdapter(notesList, getContext(), new NotesAdapter.onViewClicked() {
            @Override
            public void onOPtionMenueClicked(View view, int position) {
                notesAdapter.notifyDataSetChanged();
//                PopupMenu menu = new PopupMenu(getContext(),v);
//                menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        deleteNote(item.getItemId());
//
//                        return false;
//                    }
//                });
//                menu.show();
            }
        });
        notesRv.setAdapter(notesAdapter);
    }
    //=================================================
    private void getAllNotes() {
        notesList.clear();
        try {
            notesList.addAll(new GetAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //=======================================
    private void checkForNotes() {

        if (notesList.isEmpty()) {
            notesRv.setVisibility(View.GONE);
            // notesIv.setVisibility(View.VISIBLE);
        } else {
            // notesIv.setVisibility(View.GONE);
            notesRv.setVisibility(View.VISIBLE);
        }
    }

    //=======================================
    private void deleteNote(int position) {
        NotesModel note = notesList.get(position);
        new DeleteAsyncTask(RoomFactory.getTaskessDb(requireContext()).getTaskesDao()).execute(note);
        getAllNotes();
        notesAdapter.notifyDataSetChanged();
        checkForNotes();

    }
}