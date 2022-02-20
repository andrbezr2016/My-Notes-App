package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.configuration.ConfigProperties;
import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.NoteAddRequestDto;
import com.andrbezr2016.mynotes.dto.NoteDto;
import com.andrbezr2016.mynotes.dto.NoteEditRequestDto;
import com.andrbezr2016.mynotes.entities.Note;
import com.andrbezr2016.mynotes.exceptions.MyNotesAppException;
import com.andrbezr2016.mynotes.repositories.CategoryRepository;
import com.andrbezr2016.mynotes.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.andrbezr2016.mynotes.constants.ExceptionConstants.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;
    private final RequestContext requestContext;
    private final ConfigProperties properties;

    public List<NoteDto> getUserNotes(Long categoryId) {
        List<Note> noteList;
        if (categoryId == null) {
            noteList = noteRepository.findAllByUserIdAndDeletedFlag(requestContext.getUserId(), false);
        } else {
            checkCategory(categoryId);
            noteList = noteRepository.findAllByUserIdAndCategoryIdAndDeletedFlag(requestContext.getUserId(), categoryId, false);
        }
        return toDtoList(noteList);
    }

    public NoteDto addNote(NoteAddRequestDto noteAddRequestDto) {
        if (noteAddRequestDto.getCategoryId() != null) {
            checkCategory(noteAddRequestDto.getCategoryId());
        }
        Note note = noteRepository.save(Note.builder()
                .userId(requestContext.getUserId())
                .categoryId(noteAddRequestDto.getCategoryId())
                .title(noteAddRequestDto.getTitle())
                .content(noteAddRequestDto.getContent())
                .deletedFlag(false)
                .build());
        log.debug("Added note with id: " + note.getId());
        return toDto(note);
    }

    public NoteDto editNote(Long noteId, NoteEditRequestDto noteEditRequestDto) {
        if (noteEditRequestDto.getCategoryId() != null) {
            checkCategory(noteEditRequestDto.getCategoryId());
        }
        Note note = findNote(noteId);
        boolean isEdit = false;
        if (!Objects.equals(noteEditRequestDto.getCategoryId(), note.getCategoryId())) {
            note.setCategoryId(noteEditRequestDto.getCategoryId());
            isEdit = true;
        }
        if (noteEditRequestDto.getTitle() != null && !noteEditRequestDto.getTitle().equals(note.getTitle())) {
            note.setTitle(noteEditRequestDto.getTitle());
            isEdit = true;
        }
        if (noteEditRequestDto.getContent() != null && !noteEditRequestDto.getContent().equals(note.getContent())) {
            note.setContent(noteEditRequestDto.getContent());
            isEdit = true;
        }
        if (isEdit) {
            note = noteRepository.save(note);
            log.debug("Modified note with id: " + noteId);
        }
        return toDto(note);
    }

    public NoteDto deleteNote(Long noteId) {
        Note note = findNote(noteId);
        if (note.getDeletedFlag()) {
            noteRepository.delete(note);
            log.debug("Deleted note with id: " + noteId + ". Finally");
        } else {
            note.setDeletedFlag(true);
            note.setDeletedAt(OffsetDateTime.now());
            note = noteRepository.save(note);
            log.debug("Deleted note with id: " + noteId + ". To trash");
        }
        return toDto(note);
    }

    public NoteDto restoreNote(Long noteId) {
        Note note = findNote(noteId);
        if (note.getDeletedFlag()) {
            note.setDeletedFlag(false);
            note.setDeletedAt(null);
            note = noteRepository.save(note);
            log.debug("Restored note with id: " + noteId);
        }
        return toDto(note);
    }

    public List<NoteDto> getDeletedUserNotes() {
        List<Note> deletedNoteList = noteRepository.deleteAllByUserIdAndDeletedFlagAndDeletedAtBefore(
                requestContext.getUserId(),
                true,
                OffsetDateTime.now().minusMinutes(properties.getTrashExpiredIn()));
        if (!deletedNoteList.isEmpty()) {
            log.debug("Deleted " + deletedNoteList.size() + " notes from trash after expiration date for user with id: " + requestContext.getUserId());
        }
        List<Note> noteList = noteRepository.findAllByUserIdAndDeletedFlag(requestContext.getUserId(), true);
        return toDtoList(noteList);
    }

    private Note findNote(Long noteId) {
        return noteRepository.findByIdAndUserId(noteId, requestContext.getUserId()).orElseThrow(() -> {
            log.warn("User with id: " + requestContext.getUserId() + " has no note with id: " + noteId);
            return new MyNotesAppException(EXCEPTION_NOTE_NOT_FOUND);
        });
    }

    private NoteDto toDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .categoryId(note.getCategoryId())
                .title(note.getTitle())
                .content(note.getContent())
                .deletedAt(note.getDeletedAt())
                .createdAt(note.getCreatedAt())
                .modifiedAt(note.getModifiedAt())
                .build();
    }

    private List<NoteDto> toDtoList(List<Note> noteList) {
        return noteList.stream().map(note -> NoteDto.builder()
                .id(note.getId())
                .categoryId(note.getCategoryId())
                .title(note.getTitle())
                .content(note.getContent())
                .deletedAt(note.getDeletedAt())
                .createdAt(note.getCreatedAt())
                .modifiedAt(note.getModifiedAt())
                .build()).collect(Collectors.toList());
    }

    private void checkCategory(Long categoryId) {
        categoryRepository.findByIdAndUserId(categoryId, requestContext.getUserId()).orElseThrow(() -> {
            log.warn("User with id: " + requestContext.getUserId() + " has no category with id: " + categoryId);
            return new MyNotesAppException(EXCEPTION_CATEGORY_NOT_FOUND);
        });
    }
}
