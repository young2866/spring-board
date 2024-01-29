package com.encore.board.author.controller;

import com.encore.board.author.dto.request.AuthorSaveRequestDto;
import com.encore.board.author.dto.request.AuthorUpdateRequestDto;
import com.encore.board.author.dto.response.AuthorListResponseDto;
import com.encore.board.author.service.AuthorService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/author")
public class AuthorController {

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/create")
	public String authorCreate() {
		return "author/author-create";
	}

	@PostMapping("/create")
	public String authorCreate(AuthorSaveRequestDto authorSaveRequestDto) {
		try {
			authorService.save(authorSaveRequestDto);
			return "redirect:/author/list";
		} catch (IllegalArgumentException e) {

			return "redirect:/author/create";
		}
	}

	@GetMapping("/list")
	public String authorList(Model model) {
		List<AuthorListResponseDto> all = authorService.findAll();
		model.addAttribute("authors", all);
		return "author/author-list";
	}

	@GetMapping("/detail/{id}")
	public String authorDetail(@PathVariable Long id, Model model) {
		model.addAttribute("author", authorService.findAuthorDetail(id));
		return "author/author-detail";
	}

	@PostMapping("/update")
	public String authorUpdate(AuthorUpdateRequestDto authorUpdateRequestDto) {
		Long update = authorService.update(authorUpdateRequestDto);

		return "redirect:/author/detail/" + update;
	}

	@GetMapping("/delete/{id}")
	public String authorDelete(@PathVariable Long id) {
		authorService.deleteAuthor(id);

		return "redirect:/author/list";
	}

}