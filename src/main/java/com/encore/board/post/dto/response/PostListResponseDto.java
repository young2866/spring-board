package com.encore.board.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostListResponseDto {
	private Long id;
	private String title;
	private LocalDateTime createdTime;
	private String author_email;

}