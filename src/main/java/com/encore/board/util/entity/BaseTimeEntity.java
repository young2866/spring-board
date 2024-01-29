package com.encore.board.util.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdTime;

	@LastModifiedDate
	private LocalDateTime modifiedTime;

	@PrePersist
	public void prePersist() {
		this.createdTime = LocalDateTime.now();
		this.modifiedTime = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.modifiedTime = LocalDateTime.now();
	}

}