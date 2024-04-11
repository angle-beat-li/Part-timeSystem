package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.dto.PostDto;
import com.liy.parttimesystem.entity.Post;
import com.liy.parttimesystem.mapper.PostMapper;
import com.liy.parttimesystem.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PostServiceImp$
 *
 * @author liy
 * @date 2024/4/11$
 */
@Service
public class PostServiceImp extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;
    @Override
    public List<PostDto> listByThing(List<Long> thingList) {
        return postMapper.getPostDto(thingList);
    }
}
