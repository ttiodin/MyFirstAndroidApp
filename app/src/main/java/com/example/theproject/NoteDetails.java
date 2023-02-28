package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class NoteDetails extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText;
    private ImageButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.note_title);
        descriptionEditText = findViewById(R.id.note_description);
        saveButton = findViewById(R.id.save_note_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteDescription = descriptionEditText.getText().toString();
        if (noteTitle == null || noteTitle.isEmpty()) {
            titleEditText.setError("Title is required!");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setDescription(noteDescription);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(Note note) {
        DocumentReference docReference;

        docReference = (DocumentReference) getCollectionReferenceForNotes().document();

        docReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NoteDetails.this, "Note added successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NoteDetails.this, "Failed to add note.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }
}