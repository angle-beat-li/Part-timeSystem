package com.liy.parttimesystem.dto;

import com.liy.parttimesystem.entity.Thing;
import lombok.Data;

import java.util.List;

/**
 * ThingDto$
 *
 * @author liy
 * @date 2024/3/21$
 */
@Data
public class ThingDto extends Thing {
    Long classificationId;
    List<Long> tags;
    Long userId;
}
