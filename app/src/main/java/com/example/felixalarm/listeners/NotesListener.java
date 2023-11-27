package com.example.felixalarm.listeners;

import com.example.felixalarm.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
