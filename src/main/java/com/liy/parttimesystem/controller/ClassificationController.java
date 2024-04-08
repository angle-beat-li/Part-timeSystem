package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.entity.Classification;
import com.liy.parttimesystem.service.ClassificationService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassificationController$
 *
 * @author liy
 * @date 2024/3/21$
 */
@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Autowired
    ClassificationService classificationService;

    /**
     * @description: 查询分类

     * @return:

     */
    @GetMapping("/list")
    public APIResponse list() {
         List<Classification> list = classificationService.list();
         return new APIResponse(ResponeCode.SUCCESS,"查询成功",list);
    }
    /**
     * @description: 更新分类

     * @return:

     */
    @PostMapping("/update")
    public APIResponse update(@RequestBody Classification classification){
        //1.判断该分类是否为null
        if(classification != null){
            //2.查找是否存在该分类
            if(classification.getId() != null) {
                //3.对分类进行更新
                if (classificationService.updateById(classification))
                //4.成功返回
                return new APIResponse(ResponeCode.SUCCESS,"更新成功",classification);
            }

        }

        //5.失败返回
        return new APIResponse(ResponeCode.FAIL,"更新失败");
    }
    /**
     * @description: 删除分类

     * @return:

     */
    @PostMapping("/delete")
    public APIResponse delete(String ids){
        // 批量删除
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            classificationService.removeById(Long.parseLong(id[i]));
        }

        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }
    @PostMapping("/create")
    public APIResponse create(@RequestBody Classification classification){
        if(classification != null) {
            if(!"".equals(classification.getTitle())){
                //1.判断该分类是否存在
                LambdaQueryWrapper<Classification> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Classification::getTitle,classification.getTitle());
                //2.不存在新增
                Classification one = classificationService.getOne(queryWrapper);
                if(one == null) {
                    Classification newClassification = new Classification();
                    newClassification.setTitle(classification.getTitle());
                    newClassification.setCreateTime(NowDataTimeUtils.getNowTime());
                    classificationService.save(newClassification);
                    return new APIResponse(ResponeCode.SUCCESS,"创建成功",newClassification);
                }
            }

        }
        //3.存在返回
        return new APIResponse(ResponeCode.FAIL,"创建失败");
    }
}
