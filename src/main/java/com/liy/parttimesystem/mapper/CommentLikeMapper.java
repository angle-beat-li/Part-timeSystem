package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.entity.CommentLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * CommentLikeMapper$
 *
 * @author liy
 * @date 2024/4/4$
 */
@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike> {
    @Delete("delete from comment_like where thing_id = #{thingId}")
    void deleteByThing(Long thingId);
}
