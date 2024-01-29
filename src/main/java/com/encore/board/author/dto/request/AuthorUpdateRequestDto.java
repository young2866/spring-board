package com.encore.board.author.dto.request;

import lombok.Data;

@Data
public class AuthorUpdateRequestDto {
	private String name;
	private String email;
	private String password;
}