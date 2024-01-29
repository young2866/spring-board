package com.encore.board.post.controller;

import com.encore.board.post.dto.request.PostCreateRequestDto;
import com.encore.board.post.dto.request.PostUpdateRequestDto;
import com.encore.board.post.dto.response.PostListResponseDto;
import com.encore.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/list")
	public String allPost(Model model, @PageableDefault(size = 5, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("Posts", postService.findAllJson(pageable));
		return "/post/post-list";
	}
	@GetMapping("/json/list")
	@ResponseBody
	public Page<PostListResponseDto> allPost(Pageable pageable) {
		Page<PostListResponseDto> postListResponseDtos = postService.findAllJson(pageable);
		return postListResponseDtos;
	}

	@GetMapping("/detail/{id}")
	public String postDetail(@PathVariable Long id, Model model) {
		model.addAttribute("post",postService.findPostDetail(id));
		return "/post/post-detail";

	}

	@PostMapping("/create")
	public String createPost(PostCreateRequestDto postCreateRequestDto) {
		postService.createPost(postCreateRequestDto);
		return "redirect:/post/list";
	}
	@GetMapping("/create")
	public String createPost() {
		return "/post/post-create";
	}

	@PostMapping("/update/{id}")
	public String updatePost(@PathVariable Long id, PostUpdateRequestDto postUpdateRequestDto) {
		Long returnId = postService.updatePost(id, postUpdateRequestDto);
		return "redirect:/post/detail/" + returnId;
	}

	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable Long id) {
		postService.deletePost(id);
		return "redirect:/post/list";
	}
}