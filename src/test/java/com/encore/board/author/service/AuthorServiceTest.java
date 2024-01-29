package com.encore.board.author.service;

import com.encore.board.author.domain.Author;
import com.encore.board.author.dto.request.AuthorUpdateRequestDto;
import com.encore.board.author.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AuthorServiceTest {

	@Autowired
	private AuthorService authorService;

	@MockBean
	private AuthorRepository authorRepository;

	@Test
	public void updateTest() throws Exception {

	    //given
		Long authorId = 1L;
	    Author author = Author.builder()
			.name("test1")
			.email("test1.naver.com")
			.password("test")
			.build();
		Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
		AuthorUpdateRequestDto authorUpdateRequestDto = new AuthorUpdateRequestDto();
		authorUpdateRequestDto.setName("test2");
		authorUpdateRequestDto.setPassword("test2");
		authorUpdateRequestDto.setEmail("test1.naver.com");
		authorService.update(authorUpdateRequestDto);
	    //when

	    //then
	}


	@Test
	public void findAllTest() throws Exception {
	    //given
		List<Author> authors = new ArrayList<>();
		authors.add(new Author());
		authors.add(new Author());
		Mockito.when(authorRepository.findAll()).thenReturn(authors);
		Assertions.assertEquals(2, authorService.findAll().size());
	}


}