package com.myblog.myblog.controller;

import com.myblog.myblog.payload.CommentDto;
import com.myblog.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/post/1/comments
    @PostMapping("/{postID}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postID") long postID, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postID,commentDto), HttpStatus.CREATED);
    }
    //http://localhost:8080/api/post/1/comments
    @GetMapping("/{postID}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postID") Long postID){
        return commentService.getCommentsByPostId(postID);
    }
    //http://localhost:8080/api/post/1/comments/1
    @GetMapping("/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value = "postId") Long postID, @PathVariable(value = "id") Long commentId){
        CommentDto dto = commentService.getCommentById(postID, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/post/{postId}/comments/{id}
    @PutMapping("{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId,id,commentDto);
                return new ResponseEntity<>(dto,HttpStatus.OK);
    }
   //http://localhost:8080/api/post/{postId}/comments/{id}
    @DeleteMapping("{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("id") long id
    ){
        commentService.deleteComment(postId,id);
        return  new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
