package com.example.demo3.Mapper;

import com.example.demo3.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 李彤彤
 * @since 2023-03-05
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
