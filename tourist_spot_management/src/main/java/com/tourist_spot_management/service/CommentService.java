package com.tourist_spot_management.service;


import com.tourist_spot_management.entity.Comment;
import com.tourist_spot_management.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO);

    List<CommentDTO> getAllComments();

    CommentDTO getCommentById(Long commentId);

    CommentDTO updateComment(Long commentId, CommentDTO commentDTO);

    void deleteComment(Long commentId);

    List<CommentDTO> getCommentByPostId(Long postId);

}
