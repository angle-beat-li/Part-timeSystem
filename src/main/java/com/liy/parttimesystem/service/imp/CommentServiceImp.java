package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.dto.CommentDto;
import com.liy.parttimesystem.entity.Comment;
import com.liy.parttimesystem.mapper.CommentMapper;
import com.liy.parttimesystem.service.CommentService;
import com.sun.org.apache.bcel.internal.generic.LADD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CommentServiceImp$
 *
 * @author liy
 * @date 2024/3/27$
 */
@Service
public class CommentServiceImp extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<CommentDto> getListByThing(Long thingId, String order) {

        List<CommentDto> list = commentMapper.selectThingCommentList(thingId,order);
        return list;
    }

    @Override
    public List<Comment> getListByUser(Long userId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getUserId,userId);
        List<Comment> list = list(queryWrapper);
        return list;
    }

    @Override
    public List<CommentDto> getList() {
        return commentMapper.getList();
    }
}
