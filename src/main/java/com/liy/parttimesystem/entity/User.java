package  com.liy.parttimesystem.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@TableName("User")
public class User {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private Long phone;
  private String nickname;
  private String password;
  private Integer sex;
  private String address;
  private Integer status;
  private Integer role;
  private String createTime;
  private String email;
  private String pushEmail;
  private String description;
  private String updateTime;
  private String education;
  private String school;
  private Integer pushSwitch;
  @TableField(exist = false)
  private MultipartFile cover;
  private  String coverName;

}
