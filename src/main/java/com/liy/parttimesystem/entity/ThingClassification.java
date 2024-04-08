package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("thing_classification")
public class ThingClassification {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private Long thingId;
  private Long classificationId;

}
