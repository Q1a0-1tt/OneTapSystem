package com.example.demo3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 李彤彤
 * @since 2023-04-05
 */
@Getter
@Setter
  @TableName("sys_message")
@ApiModel(value = "Message对象", description = "")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
      @ApiModelProperty("id")
        private Integer id;

      @ApiModelProperty("留言名称")
      private String author;

      @ApiModelProperty("时间")
      private Date time;

      @ApiModelProperty("头像")
      private String img;

      @ApiModelProperty("留言内容")
      private String content;


}
