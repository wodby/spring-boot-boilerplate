package com.example.app;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

	List<Note> findTop20ByOrderByCreatedAtDescIdDesc();

}
