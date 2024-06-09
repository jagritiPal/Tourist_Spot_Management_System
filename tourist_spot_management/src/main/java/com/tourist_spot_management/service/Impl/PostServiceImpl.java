package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Post;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.CommentDTO;
import com.tourist_spot_management.payload.PostDTO;
import com.tourist_spot_management.repository.PostRepository;
import com.tourist_spot_management.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return mapToDto(savedPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return mapToDto(post);
    }

    @Override
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        existingPost.setTitle(postDTO.getTitle());
        existingPost.setDescription(postDTO.getDescription());
        existingPost.setContent(postDTO.getContent());

        Post updatedPost = postRepository.save(existingPost);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        postRepository.deleteById(postId);
    }

    @Override
    public PostDTO getAllCommentsForParticularPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        List<CommentDTO> commentDTOs = post.getComments().stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());

        PostDTO postDTO = mapToDto(post);
        postDTO.setComments(commentDTOs);

        return postDTO;
    }



    private PostDTO mapToDto(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    private Post mapToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }
}
