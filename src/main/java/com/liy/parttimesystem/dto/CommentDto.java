package com.liy.parttimesystem.dto;

import com.liy.parttimesystem.entity.Comment;
import lombok.Data;

/**
 * CommentDto$
 *
 * @author liy
 * @date 2024/3/27$
 */
@Data
public class CommentDto extends Comment {

    private int likeCount;
    private String username;
    private String title;
}
