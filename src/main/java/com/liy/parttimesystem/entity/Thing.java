package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("thing")
public class Thing {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private String title;
  private String salary;
  private String description;
  private String education;
  private String workExpe;
  private String location;
  private Integer status;
  private String createTime;
  private String updateTime;

}
