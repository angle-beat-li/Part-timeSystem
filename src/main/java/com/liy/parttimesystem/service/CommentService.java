package com.liy.parttimesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liy.parttimesystem.dto.CommentDto;
import com.liy.parttimesystem.entity.Comment;

import java.util.List;

/**
 * CommentService$
 *
 * @author liy
 * @date 2024/3/27$
 */
public interface CommentService extends IService<Comment> {
    List<CommentDto> getListByThing(Long thingId, String order);
    List<Comment> getListByUser(Long userId);
    List<CommentDto> getList();
}
