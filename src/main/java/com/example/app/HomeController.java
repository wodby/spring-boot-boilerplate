package com.example.app;

import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** Renders the landing page and stores the notes submitted from it. */
@Controller
public class HomeController {

	private final NoteRepository notes;

	public HomeController(NoteRepository notes) {
		this.notes = notes;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("notes", notes.findTop20ByOrderByCreatedAtDescIdDesc());
		model.addAttribute("count", notes.count());
		model.addAttribute("maxLength", Note.MAX_TEXT_LENGTH);
		model.addAttribute("javaVersion", Runtime.version().toString());
		model.addAttribute("springBootVersion", SpringBootVersion.getVersion());
		return "index";
	}

	@PostMapping("/notes")
	public String add(@RequestParam("text") String text) {
		String trimmed = text.strip();
		if (!trimmed.isEmpty() && trimmed.length() <= Note.MAX_TEXT_LENGTH) {
			notes.save(new Note(trimmed));
		}
		// Redirect after the form post so a page reload does not submit it again.
		return "redirect:/";
	}

}
