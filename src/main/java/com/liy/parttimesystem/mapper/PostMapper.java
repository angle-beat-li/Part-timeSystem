package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.dto.PostDto;
import com.liy.parttimesystem.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PostMapper$
 *
 * @author liy
 * @date 2024/4/11$
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

    List<PostDto> getPostDto(List<Long> thingIds);
}
