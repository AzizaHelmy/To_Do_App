package com.example.todoapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Async2.DeleteAsyncTask;
import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.R;
import com.example.todoapp.RoomDB.RoomFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
List<NotesModel> notesList;
Context context;
onViewClicked onOptionClicked;

public  interface onViewClicked{
    void onOPtionMenueClicked(View view,int position);
}

    public NotesAdapter(List<NotesModel> notesList, Context context, onViewClicked onOptionClicked) {
        this.notesList = notesList;
        this.context = context;
        this.onOptionClicked = onOptionClicked;
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item_rv,parent,false);
        return new NotesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
     NotesModel notesModel=  notesList.get(position);
     holder.contentOfNote.setText(notesModel.getContent());
     holder.titileofNote.setText(notesModel.getTitile());
     holder.dateOfNote.setText(notesModel.getDate());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mCardView.setCardBackgroundColor(holder.itemView.getResources().getColor(getRandemColor(),null));
        }
        holder.optionMenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu menu = new PopupMenu(context, v);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    menu.setGravity(Gravity.END);
                }
                menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
//                        String content=holder.contentOfNote.getText().toString();
//                        String titile=holder.titileofNote.getText().toString();
                        NotesModel notesModel1=notesList.get(position);
////                        String Notecontent=notesModel.getContent();
////                        String Notetitle=notesModel.getTitile();
////                        String NoteDate=notesModel.getDate();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Azza", notesModel);

                        Navigation.findNavController(v).navigate(R.id.action_stickyNotesFragment_to_editNoteFragment,bundle);
                        return false;
                    }
                });
                menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        deleteNote(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        return false;
                    }
                });
                menu.show();
            }
        });

    }
    //===================================================================
    private void deleteNote(int position) {
        NotesModel note = notesList.get(position);
        new DeleteAsyncTask(RoomFactory.getTaskessDb(context).getTaskesDao()).execute(note);

       notifyDataSetChanged();
    }
//=======================================================================
    private int getRandemColor() {
        List<Integer>colorCode=new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yello);
        colorCode.add(R.color.green);
        colorCode.add(R.color.ohh);
        colorCode.add(R.color.orang);
        colorCode.add(R.color.move);
        colorCode.add(R.color.mint);
        colorCode.add(R.color.yylow);
        colorCode.add(R.color.blue);
        colorCode.add(R.color.ohh);
        colorCode.add(R.color.orang);
        colorCode.add(R.color.yylow);
        colorCode.add(R.color.green);
        Random randemColor=new Random();
        int number=randemColor.nextInt(colorCode.size());
        return colorCode.get(number);
    }
//====================================================================
    @Override
    public int getItemCount() {
        return notesList.size();
    }

    //====================================================
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView contentOfNote;
        TextView titileofNote;
        TextView dateOfNote;
        ImageButton optionMenue;
        CardView mCardView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            contentOfNote=itemView.findViewById(R.id.content_tv);
            titileofNote=itemView.findViewById(R.id.title_tv);
            dateOfNote=itemView.findViewById(R.id.date_tv);
            optionMenue=itemView.findViewById(R.id.menue_ib);
            mCardView=itemView.findViewById(R.id.note_card);

        }
    }
}
