package com.andrbezr2016.mynotes.services;

import com.andrbezr2016.mynotes.contexts.RequestContext;
import com.andrbezr2016.mynotes.dto.NoteDto;
import com.andrbezr2016.mynotes.entities.Category;
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

    public NoteDto addNote(NoteDto noteDto) {
        if (noteDto.getCategoryId() != null) {
            checkCategory(noteDto.getCategoryId());
        }
        OffsetDateTime currentTime = OffsetDateTime.now();
        Note note = noteRepository.save(Note.builder()
                .id(noteDto.getId())
                .userId(requestContext.getUserId())
                .categoryId(noteDto.getCategoryId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .deletedFlag(false)
                .createdAt(currentTime)
                .modifiedAt(currentTime)
                .build());
        log.info("Added note with id: " + note.getId());
        return toDto(note);
    }

    public NoteDto editNote(Long noteId, NoteDto noteDto) {
        if (noteDto.getCategoryId() != null) {
            checkCategory(noteDto.getCategoryId());
        }
        Note note = findNote(noteId);
        boolean isEdit = false;
        if (!Objects.equals(noteDto.getCategoryId(), note.getCategoryId())) {
            note.setCategoryId(noteDto.getCategoryId());
            isEdit = true;
        }
        if (noteDto.getTitle() != null) {
            note.setTitle(noteDto.getTitle());
            isEdit = true;
        }
        if (noteDto.getContent() != null) {
            note.setContent(noteDto.getContent());
            isEdit = true;
        }
        if (isEdit) {
            note.setModifiedAt(OffsetDateTime.now());
            note = noteRepository.save(note);
            log.info("Modified note with id: " + noteId);
        }
        return toDto(note);
    }

    public NoteDto deleteNote(Long noteId) {
        Note note = findNote(noteId);
        if (note.getDeletedFlag()) {
            noteRepository.delete(note);
            log.info("Deleted note with id: " + noteId + ". Finally");
        } else {
            OffsetDateTime currentTime = OffsetDateTime.now();
            note.setDeletedFlag(true);
            note.setDeletedAt(currentTime);
            note.setModifiedAt(currentTime);
            noteRepository.save(note);
            log.info("Deleted note with id: " + noteId + ". To trash");
        }
        return toDto(note);
    }

    public NoteDto restoreNote(Long noteId) {
        Note note = findNote(noteId);
        note.setDeletedFlag(false);
        note.setDeletedAt(null);
        note.setModifiedAt(OffsetDateTime.now());
        noteRepository.save(note);
        log.info("Restored note with id: " + noteId);
        return toDto(note);
    }

    public List<NoteDto> getDeletedUserNotes() {
        List<Note> noteList = noteRepository.findAllByUserIdAndDeletedFlag(requestContext.getUserId(), true);
        return toDtoList(noteList);
    }

    private Note findNote(Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> {
            log.info("Not found note with id: " + noteId);
            return new MyNotesAppException(EXCEPTION_NOTE_NOT_FOUND);
        });
        if (note.getUserId().equals(requestContext.getUserId())) {
            return note;
        } else {
            log.info("User with id: " + requestContext.getUserId() + " no access to note with id: " + noteId);
            throw new MyNotesAppException(EXCEPTION_NO_ACCESS_TO_NOTE);
        }
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
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.info("Not found category with id: " + categoryId);
            return new MyNotesAppException(EXCEPTION_CATEGORY_NOT_FOUND);
        });
        if (!category.getUserId().equals(requestContext.getUserId())) {
            log.info("User with id: " + requestContext.getUserId() + " no access to category with id: " + categoryId);
            throw new MyNotesAppException(EXCEPTION_NO_ACCESS_TO_CATEGORY);
        }
    }
}
