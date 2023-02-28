package com.example.theproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {

    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Note note) {
        Timestamp test = note.timestamp;
        String convertedTimeStamp = new SimpleDateFormat("MM/dd/yyyy").format(test.toDate());

        holder.noteTitle.setText(note.title);
        holder.noteDescription.setText(note.description);
        holder.noteTimeStamp.setText(convertedTimeStamp);

        //This is the code that will bring up the NoteDetails for the user to Edit the Note.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,NoteDetails.class);
                intent.putExtra("title",note.title);
                intent.putExtra("description",note.description);
                String docId = getSnapshots().getSnapshot(position).getId();
                intent.putExtra("docId",docId);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_notes,parent,false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle, noteDescription, noteTimeStamp;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteDescription = itemView.findViewById(R.id.note_description);
            noteTimeStamp = itemView.findViewById(R.id.note_timestamp);

        }
    }

}
