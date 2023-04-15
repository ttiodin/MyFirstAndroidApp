package com.example.theproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private TextView pageTitle, deleteNote;
    private String title,description,docId;
    private boolean toEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.note_title);
        descriptionEditText = findViewById(R.id.note_description);
        saveButton = findViewById(R.id.save_note_button);
        pageTitle = findViewById(R.id.add_note_title);
        deleteNote = findViewById(R.id.delete_note);

        //This happens the the user wants to edit the notes.
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()){
            toEdit = true;
        }

        titleEditText.setText(title);
        descriptionEditText.setText(description);

        if(toEdit){
            pageTitle.setText("Edit Note");
            deleteNote.setVisibility(View.VISIBLE);
        }

        //On click Listener for the save Button.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        //On Click Listener for the Delete Text.
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNoteFromFirebase();
            }
        });
    }

    //This method will delete the note from Firebase.
    private void deleteNoteFromFirebase() {

        DocumentReference docReference;
        docReference = getCollectionReferenceForNotes().document(docId);

        docReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NoteDetails.this, "Note deleted successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NoteDetails.this, "Failed to delete note.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //This method is used to save the note to Firebase.
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

    //This method is used to save the note to Firebase.
    void saveNoteToFirebase(Note note) {
        DocumentReference docReference;


        if(toEdit) {
            //This updates the note.
            docReference = getCollectionReferenceForNotes().document(docId);
        } else {
            //This Creates a new note.
            docReference = getCollectionReferenceForNotes().document();
        }



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

    //This pulls all the notes relating to the specific user that is logged in.
    private CollectionReference getCollectionReferenceForNotes() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("notes")
                .document(currentUser.getUid()).collection("my_notes");
    }
}