package com.liy.parttimesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liy.parttimesystem.dto.PostDto;
import com.liy.parttimesystem.entity.Post;

import java.util.List;

/**
 * PostService$
 *
 * @author liy
 * @date 2024/4/11$
 */
public interface PostService extends IService<Post> {
    List<PostDto> listByThing(List<Long> thingList);
}
