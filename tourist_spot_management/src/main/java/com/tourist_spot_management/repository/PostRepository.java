package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
