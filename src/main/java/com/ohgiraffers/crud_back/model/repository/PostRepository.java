package com.ohgiraffers.crud_back.model.repository;

import com.ohgiraffers.crud_back.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
