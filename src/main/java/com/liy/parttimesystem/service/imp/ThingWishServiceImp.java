package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.ThingWish;
import com.liy.parttimesystem.mapper.ThingMapper;
import com.liy.parttimesystem.mapper.ThingWishMapper;
import com.liy.parttimesystem.service.ThingWishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ThingWishServiceImp$
 *
 * @author liy
 * @date 2024/3/27$
 */
@Service
public class ThingWishServiceImp extends ServiceImpl<ThingWishMapper, ThingWish> implements ThingWishService {

    @Autowired
    ThingMapper thingMapper;
    @Override
    public List<Thing> getByUserId(Long userId) {
        LambdaQueryWrapper<ThingWish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ThingWish::getUserId,userId);
        List<ThingWish> list = list(queryWrapper);
        final List<Long> thingIdList = list.stream().map(item -> {
            return item.getThingId();
        }).collect(Collectors.toList());
        List<Thing> things = null;
        if(!thingIdList.isEmpty()) {
            LambdaQueryWrapper<Thing> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Thing::getId,thingIdList);
            things = thingMapper.selectList(lambdaQueryWrapper);
        }
        return things;
    }
}
