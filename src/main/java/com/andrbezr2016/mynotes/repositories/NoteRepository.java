package com.andrbezr2016.mynotes.repositories;

import com.andrbezr2016.mynotes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserIdAndDeletedFlag(Long userId, Boolean b);

    List<Note> findAllByUserIdAndCategoryIdAndDeletedFlag(Long userId, Long categoryId, Boolean b);
}
