package com.encore.board.post.dto.request;

import lombok.Data;

@Data
public class PostUpdateRequestDto {
	private String title;
	private String contents;

}