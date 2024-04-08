package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.Comment;
import com.liy.parttimesystem.entity.CommentLike;
import com.liy.parttimesystem.mapper.CommentLikeMapper;
import com.liy.parttimesystem.mapper.CommentMapper;
import com.liy.parttimesystem.service.CommentLikeService;
import com.liy.parttimesystem.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * CommentLikeServiceImp$
 *
 * @author liy
 * @date 2024/4/4$
 */
@Service
public class CommentLikeServiceImp extends ServiceImpl<CommentLikeMapper, CommentLike> implements CommentLikeService {
}
