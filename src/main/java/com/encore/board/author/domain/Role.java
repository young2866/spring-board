package com.encore.board.author.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
	ADMIN("ADMIN"),
	USER("USER");

	private final String role;

	public String getRoleName() {
		return role;
	}
}
