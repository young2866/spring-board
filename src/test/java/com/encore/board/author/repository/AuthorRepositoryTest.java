package com.encore.board.author.repository;


import com.encore.board.author.domain.Author;
import com.encore.board.author.domain.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthorRepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	public void 유저_저장_테스트() throws Exception {
	    //given
		Author author = Author.builder()
			.name("Test용이름")
			.email("Test@Test.co.kr")
			.password("Test1234")
			.role(Role.ADMIN).build();
	    //when
		authorRepository.save(author);
		Author savedAuthor = authorRepository.findByEmail("Test@Test.co.kr").orElse(null);
	    //then
		Assertions.assertEquals(author.getEmail(), savedAuthor.getEmail());
	}


}