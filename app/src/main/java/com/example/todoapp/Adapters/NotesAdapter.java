package com.example.todoapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.Models.NotesModel;
import com.example.todoapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
List<NotesModel> notesList;
Context context;


    public NotesAdapter(List<NotesModel> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
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
    // holder.dateOfNote.setText(notesModel.getDate());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.mCardView.setCardBackgroundColor(holder.itemView.getResources().getColor(getRandemColor(),null));
        }
        //holder.optionMenue

    }

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

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    //====================================================
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView contentOfNote;
        TextView titileofNote;
        //TextView dateOfNote;
        ImageButton optionMenue;
        CardView mCardView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            contentOfNote=itemView.findViewById(R.id.content_tv);
            titileofNote=itemView.findViewById(R.id.title_tv);
            //dateOfNote=itemView.findViewById(R.id.date_tv);
            optionMenue=itemView.findViewById(R.id.menue_ib);
            mCardView=itemView.findViewById(R.id.note_card);

        }
    }
}
