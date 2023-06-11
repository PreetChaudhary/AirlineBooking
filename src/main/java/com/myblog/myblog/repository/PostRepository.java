package com.myblog.myblog.repository;

import com.myblog.myblog.entity.post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<post,Long> {
}
