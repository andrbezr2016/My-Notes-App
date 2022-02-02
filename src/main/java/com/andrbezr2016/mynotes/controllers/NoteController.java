package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.NoteDto;
import com.andrbezr2016.mynotes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_NOTES_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_NOTES_PATH)
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public List<NoteDto> getUserNotes(@RequestParam(required = false) Long categoryId) {
        return noteService.getUserNotes(categoryId);
    }

    @PostMapping
    public NoteDto addNote(@RequestBody NoteDto noteDto) {
        return noteService.addNote(noteDto);
    }

    @PatchMapping("/{noteId}")
    public NoteDto editNote(@PathVariable Long noteId, @RequestBody NoteDto noteDto) {
        return noteService.editNote(noteId, noteDto);
    }

    @DeleteMapping("/{noteId}")
    public NoteDto deleteNote(@PathVariable Long noteId) {
        return noteService.deleteNote(noteId);
    }

    @PostMapping("/{noteId}")
    public NoteDto restoreNote(@PathVariable Long noteId) {
        return noteService.restoreNote(noteId);
    }

    @GetMapping("/deleted")
    public List<NoteDto> getDeletedUserNotes() {
        return noteService.getDeletedUserNotes();
    }
}
