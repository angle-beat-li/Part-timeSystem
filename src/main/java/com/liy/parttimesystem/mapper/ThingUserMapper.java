package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.entity.ThingUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * ThingUserMapper$
 *
 * @author liy
 * @date 2024/3/23$
 */
@Mapper
public interface ThingUserMapper extends BaseMapper<ThingUser> {
//    @Delete("delete from thing_user where thing_id = #{thingId}")
    void deleteByThing(long thingId);
}
