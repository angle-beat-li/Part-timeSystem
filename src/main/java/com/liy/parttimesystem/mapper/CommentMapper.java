package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.dto.CommentDto;
import com.liy.parttimesystem.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CommentMapper$
 *
 * @author liy
 * @date 2024/3/27$
 */
@Mapper
public interface CommentMapper  extends BaseMapper<Comment> {
    List<CommentDto> selectThingCommentList(Long thingId, String order );
    List<CommentDto> getList();
}
