package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.dto.NoteDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    public List<NoteDto> getUserNotes(String categoryId) {
        return new ArrayList<>();
    }

    public NoteDto addNote(NoteDto noteDto) {
        return null;
    }

    public NoteDto editNote(Long noteId, NoteDto noteDto) {
        return null;
    }

    public NoteDto deleteNote(Long noteId) {
        return null;
    }

    public NoteDto restoreNote(Long noteId) {
        return null;
    }

    public List<NoteDto> getDeletedUserNotes() {
        return new ArrayList<>();
    }
}
