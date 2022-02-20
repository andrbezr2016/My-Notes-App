package com.andrbezr2016.mynotes.repositories;

import com.andrbezr2016.mynotes.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserIdAndDeletedFlag(Long userId, Boolean deletedFlag);

    List<Note> findAllByUserIdAndCategoryIdAndDeletedFlag(Long userId, Long categoryId, Boolean deletedFlag);

    Optional<Note> findByIdAndUserId(Long id, Long userId);

    @Transactional
    List<Note> deleteAllByUserIdAndDeletedFlagAndDeletedAtBefore(Long userId, Boolean deletedFlag, OffsetDateTime dateTime);
}
