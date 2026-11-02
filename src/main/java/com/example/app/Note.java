package com.example.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/** A short note stored in the notes table, which the Flyway migration creates. */
@Entity
@Table(name = "notes")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = Note.MAX_TEXT_LENGTH)
	private String text;

	@Column(name = "created_at", nullable = false)
	private Instant createdAt;

	public static final int MAX_TEXT_LENGTH = 200;

	protected Note() {
	}

	public Note(String text) {
		this.text = text;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

}
