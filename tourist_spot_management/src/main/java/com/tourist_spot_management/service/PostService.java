package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    PostDTO updatePost(Long postId, PostDTO postDTO);

    void deletePost(Long postId);

    PostDTO getAllCommentsForParticularPost(Long postId);

}
