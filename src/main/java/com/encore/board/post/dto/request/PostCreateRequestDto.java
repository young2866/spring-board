package com.encore.board.post.dto.request;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostCreateRequestDto {
	private String title;
	private String email;
	private String contents;
	private LocalDateTime test;

}