package com.encore.board.author.dto.response;

import com.encore.board.author.domain.Role;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AuthorDetailResponseDto {

	private Long id;
	private String name;
	private String email;
	private String password;
	private Role role;
	private LocalDateTime createdTime;
	private Integer postCount;


}