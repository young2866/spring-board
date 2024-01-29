package com.encore.board.post.repository;

import com.encore.board.post.domain.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	//	List<Post> findAllByOrderByCreatedTimeDesc();
	Page<Post> findAll(Pageable pageable);
	@Query("select p from Post p left join p.author")
	List<Post> findAllByJoin();

	@Query("select p from Post p left join fetch p.author")
	List<Post> findAllByFetchJoin();

}
