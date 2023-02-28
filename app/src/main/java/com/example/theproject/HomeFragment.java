package com.example.theproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {

    FloatingActionButton addNoteButton;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    public HomeFragment () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        addNoteButton = v.findViewById(R.id.add_note_btn);
        recyclerView = v.findViewById(R.id.recyclerView);

        //Sets the On Click Listener for the Floating Action Button
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNote = new Intent(getActivity(), NoteDetails.class);
                startActivity(addNote);
            }
        });

        setupRecyclerView();

        // Inflate the layout for this fragment
        return v;
    }

    //This will list the users notes.
    private void setupRecyclerView() {
        Query query = getCollectionReferenceForNotes().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter(options, getContext());
        recyclerView.setAdapter(noteAdapter);
    }

    private CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }

    public void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    public void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    public void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}