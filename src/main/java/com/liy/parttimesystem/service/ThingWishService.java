package com.liy.parttimesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liy.parttimesystem.entity.ThingWish;

import java.util.List;

/**
 * ThingWishService$
 *
 * @author liy
 * @date 2024/3/27$
 */
public interface ThingWishService extends IService<ThingWish> {

    List<ThingWish> getByUserId(Long userId);
}
