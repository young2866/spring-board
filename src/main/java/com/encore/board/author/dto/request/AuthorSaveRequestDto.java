package com.encore.board.author.dto.request;

import com.encore.board.author.domain.Role;
import lombok.Data;

@Data
public class AuthorSaveRequestDto {
	private String name;
	private String email;
	private String password;
	private Role role;
}