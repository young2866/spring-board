package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.request.AuthorSaveRequestDto;
import com.encore.board.author.dto.request.AuthorUpdateRequestDto;
import com.encore.board.author.dto.response.AuthorDetailResponseDto;
import com.encore.board.author.dto.response.AuthorListResponseDto;
import com.encore.board.author.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public void save(AuthorSaveRequestDto authorSaveRequestDto) throws IllegalArgumentException{
		if (authorRepository.findByEmail(
			authorSaveRequestDto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
		}

		Author author = Author.builder()
			.name(authorSaveRequestDto.getName())
			.password(authorSaveRequestDto.getPassword())
			.email(authorSaveRequestDto.getEmail())
			.role(authorSaveRequestDto.getRole())
			.build();

		authorRepository.save(author);
	}

	public List<AuthorListResponseDto> findAll() {
		List<AuthorListResponseDto> authorListResponseDtos = authorRepository.findAll()
			.stream()
			.map(author -> {
				AuthorListResponseDto dto = new AuthorListResponseDto();
				dto.setId(author.getId());
				dto.setName(author.getName());
				dto.setEmail(author.getEmail());
				return dto;
			})
			.collect(Collectors.toList());

		return authorListResponseDtos;
	}

	public AuthorDetailResponseDto findAuthorDetail(Long id) throws EntityNotFoundException {
		Author author = this.findById(id);
		AuthorDetailResponseDto authorDetailResponseDto = new AuthorDetailResponseDto();
		authorDetailResponseDto.setId(author.getId());
		authorDetailResponseDto.setName(author.getName());
		authorDetailResponseDto.setEmail(author.getEmail());
		authorDetailResponseDto.setPassword(author.getPassword());
		authorDetailResponseDto.setRole(author.getRole());
		authorDetailResponseDto.setPostCount(author.getPosts().size());
		authorDetailResponseDto.setCreatedTime(author.getCreatedTime());

		return authorDetailResponseDto;
	}

	public Author findByEmail(String email) throws EntityNotFoundException {
		return authorRepository.findByEmail(email)
			.orElseThrow(() -> new EntityNotFoundException("해당 Email을 가진 유저가 없습니다."));
	}

	public Author findById(Long id) throws EntityNotFoundException {
		return authorRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 유저가 없습니다."));
	}

	public Long update(AuthorUpdateRequestDto authorUpdateRequestDto)
		throws EntityNotFoundException {
		Author author = this.findByEmail(authorUpdateRequestDto.getEmail());
		author.authorUpdate(authorUpdateRequestDto.getName(), authorUpdateRequestDto.getPassword());
		authorRepository.save(author);
		return author.getId();
	}

	public void deleteAuthor(Long id) {
		Author author = authorRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 유저가 존재하지 않습니다."));
		authorRepository.delete(author);
	}
}