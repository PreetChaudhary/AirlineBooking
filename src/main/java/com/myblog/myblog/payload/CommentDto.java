package com.myblog.myblog.payload;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String name;
    private String Body;
    private String email;
}
