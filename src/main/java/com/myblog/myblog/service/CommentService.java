package com.myblog.myblog.service;

import com.myblog.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postID, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postID);
    CommentDto getCommentById(Long postID, Long commentId);

    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);
}
