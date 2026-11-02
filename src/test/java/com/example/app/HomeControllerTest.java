package com.example.app;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private NoteRepository notes;

	@Test
	void listsStoredNotes() throws Exception {
		given(notes.findTop20ByOrderByCreatedAtDescIdDesc()).willReturn(List.of(new Note("Hello from Wodby")));
		given(notes.count()).willReturn(1L);

		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("Hello from Wodby")))
			.andExpect(content().string(Matchers.containsString("1 note stored")));
	}

	@Test
	void storesTrimmedNoteAndRedirects() throws Exception {
		mvc.perform(post("/notes").param("text", "  A note  "))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));

		verify(notes).save(argThat(note -> note.getText().equals("A note")));
	}

	@Test
	void ignoresBlankAndTooLongNotes() throws Exception {
		mvc.perform(post("/notes").param("text", "   ")).andExpect(redirectedUrl("/"));
		mvc.perform(post("/notes").param("text", "x".repeat(Note.MAX_TEXT_LENGTH + 1))).andExpect(redirectedUrl("/"));

		verify(notes, never()).save(any());
	}

}
