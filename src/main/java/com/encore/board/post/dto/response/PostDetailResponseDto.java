package com.encore.board.post.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostDetailResponseDto {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdTime;
}