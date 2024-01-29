package com.encore.board.post.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.repository.AuthorRepository;
import com.encore.board.post.domain.Post;
import com.encore.board.post.dto.request.PostCreateRequestDto;
import com.encore.board.post.dto.request.PostUpdateRequestDto;
import com.encore.board.post.dto.response.PostDetailResponseDto;
import com.encore.board.post.dto.response.PostListResponseDto;
import com.encore.board.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final AuthorRepository authorRepository;

	@Autowired
	public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
		this.postRepository = postRepository;
		this.authorRepository = authorRepository;
	}


	public List<PostListResponseDto> findAllPost() {
		List<PostListResponseDto> postListResponseDtos = postRepository.findAllByFetchJoin()
			.stream()
			.map(post -> {
				PostListResponseDto dto = new PostListResponseDto();
				dto.setId(post.getId());
				dto.setTitle(post.getTitle());
				dto.setAuthor_email(post.getAuthor() == null ? "익명" : post.getAuthor().getEmail());
				return dto;
			}).collect(Collectors.toList());
		return postListResponseDtos;
	}

	public Page<PostListResponseDto> findAllJson(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		Page<PostListResponseDto> postListResponseDtos = posts.map(post -> {
			PostListResponseDto dto = new PostListResponseDto();
			dto.setId(post.getId());
			dto.setTitle(post.getTitle());
			dto.setAuthor_email(post.getAuthor() == null ? "익명" : post.getAuthor().getEmail());
			dto.setCreatedTime(post.getCreatedTime());
			return dto;
		});
		return postListResponseDtos;
	}

	public PostDetailResponseDto findPostDetail(Long id) {
		Post post = this.findById(id);
		PostDetailResponseDto postDetailResponseDto = new PostDetailResponseDto();
		postDetailResponseDto.setId(post.getId());
		postDetailResponseDto.setTitle(post.getTitle());
		postDetailResponseDto.setContents(post.getContents());
		postDetailResponseDto.setCreatedTime(post.getCreatedTime());

		return postDetailResponseDto;
	}

	public Post findById(Long id) throws EntityNotFoundException {
		return postRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 글이 존재하지 않습니다."));
	}

	public void createPost(PostCreateRequestDto postCreateRequestDto) {
		Post post = Post.builder()
			.title(postCreateRequestDto.getTitle())
			.contents(postCreateRequestDto.getContents())
			.author(findByEmail(postCreateRequestDto.getEmail()))
			.build();
		postRepository.save(post);
	}

	public Long updatePost(Long id, PostUpdateRequestDto postUpdateRequestDto) {
		Post post = this.findById(id);
		post.updatePost(postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContents());
		postRepository.save(post);
		return post.getId();
	}

	public void deletePost(Long id) {
		Post post = this.findById(id);
		postRepository.delete(post);
	}

	public Author findByEmail(String email) throws EntityNotFoundException{
		return authorRepository.findByEmail(email)
			.orElse(null);
	}

}