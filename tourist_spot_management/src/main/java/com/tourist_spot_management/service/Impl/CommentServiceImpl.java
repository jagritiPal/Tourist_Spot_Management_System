package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Comment;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.CommentDTO;
import com.tourist_spot_management.repository.CommentRepository;
import com.tourist_spot_management.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        existingComment.setText(commentDTO.getText());
        existingComment.setEmail(commentDTO.getEmail());

        Comment updatedComment = commentRepository.save(existingComment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostPostId(postId);
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CommentDTO mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapToEntity(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }
}
