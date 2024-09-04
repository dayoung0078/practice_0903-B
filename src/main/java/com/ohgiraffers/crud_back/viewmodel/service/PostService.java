package com.ohgiraffers.crud_back.viewmodel.service;

import com.ohgiraffers.crud_back.model.entity.Post;
import com.ohgiraffers.crud_back.model.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    // 의존성 주입

    final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    // 전체조회

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    //상세

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    // 생성
    public Post createPost(Post post) {
        if (post.getAuthor() == null || post.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("작성자가 비거나 널값이면 안돼유");
        }
        return postRepository.save(post);
    }

    //수정

    public Optional<Post> updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return postRepository.save(post);
                });
    }

    //삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}
