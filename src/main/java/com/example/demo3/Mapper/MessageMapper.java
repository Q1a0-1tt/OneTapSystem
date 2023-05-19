package com.example.demo3.mapper;

import com.example.demo3.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李彤彤
 * @since 2023-04-05
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
