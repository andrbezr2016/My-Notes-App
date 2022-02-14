package com.andrbezr2016.mynotes.controllers;

import com.andrbezr2016.mynotes.dto.NoteAddRequestDto;
import com.andrbezr2016.mynotes.dto.NoteDto;
import com.andrbezr2016.mynotes.dto.NoteEditRequestDto;
import com.andrbezr2016.mynotes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.andrbezr2016.mynotes.constants.ApiConstants.API_NOTES_PATH;

@Validated
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping(API_NOTES_PATH)
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public List<NoteDto> getUserNotes(@RequestParam(required = false) @Positive Long categoryId) {
        return noteService.getUserNotes(categoryId);
    }

    @PostMapping
    public NoteDto addNote(@RequestBody @Valid NoteAddRequestDto noteAddRequestDto) {
        return noteService.addNote(noteAddRequestDto);
    }

    @PatchMapping("/{noteId}")
    public NoteDto editNote(@PathVariable @Positive Long noteId, @RequestBody @Valid NoteEditRequestDto noteEditRequestDto) {
        return noteService.editNote(noteId, noteEditRequestDto);
    }

    @DeleteMapping("/{noteId}")
    public NoteDto deleteNote(@PathVariable @Positive Long noteId) {
        return noteService.deleteNote(noteId);
    }

    @PostMapping("/{noteId}")
    public NoteDto restoreNote(@PathVariable @Positive Long noteId) {
        return noteService.restoreNote(noteId);
    }

    @GetMapping("/deleted")
    public List<NoteDto> getDeletedUserNotes() {
        return noteService.getDeletedUserNotes();
    }
}
