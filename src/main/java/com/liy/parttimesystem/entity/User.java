package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("User")
public class User {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private Long phone;
  private String nickname;
  private String password;
  private String address;
  private Integer status;
  private Integer role;
  private String createTime;
  private String email;
  private String pushEmail;
  private String description;
  private String updateTime;


}
