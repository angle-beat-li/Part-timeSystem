package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.ThingClassification;
import com.liy.parttimesystem.mapper.ClassificationMapper;
import com.liy.parttimesystem.mapper.ThingClassificationMapper;
import com.liy.parttimesystem.service.ThingClassificationService;
import org.springframework.stereotype.Service;

/**
 * ThingClassificationServiceImp$
 *
 * @author liy
 * @date 2024/3/23$
 */
@Service
public class ThingClassificationServiceImp extends ServiceImpl<ThingClassificationMapper, ThingClassification> implements ThingClassificationService {
}
