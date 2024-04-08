package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.ThingWish;
import com.liy.parttimesystem.mapper.ThingWishMapper;
import com.liy.parttimesystem.service.ThingWishService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ThingWishServiceImp$
 *
 * @author liy
 * @date 2024/3/27$
 */
@Service
public class ThingWishServiceImp extends ServiceImpl<ThingWishMapper, ThingWish> implements ThingWishService {
    @Override
    public List<ThingWish> getByUserId(Long userId) {
        LambdaQueryWrapper<ThingWish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ThingWish::getUserId,userId);
        List<ThingWish> list = list(queryWrapper);
        return list;
    }
}
