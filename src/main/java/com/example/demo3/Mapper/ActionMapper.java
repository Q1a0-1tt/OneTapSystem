package com.example.demo3.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo3.entity.Action;
import com.example.demo3.entity.ActionUserView;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface ActionMapper extends BaseMapper<Action> {

    @Update("UPDATE sys_action SET joinpeople = joinpeople + 1, pid = CONCAT(IFNULL(pid, ''), ' ', CONVERT(#{userName}, CHAR)) WHERE id = #{actionId}")
    void updateJoinNum(@Param("actionId") Integer actionId, @Param("userName") String userName);

    @Select("SELECT COUNT(*) FROM sys_action WHERE id = #{actionId} AND pid LIKE CONCAT('%', #{userName}, '%'); ")
    int checkUser(@Param("actionId")Integer actionId,@Param("userName") String userName);
    @Update("UPDATE sys_action SET joinpeople = joinpeople - 1, pid = REPLACE(pid, #{userName}, '') WHERE id = #{actionId}")
    void updateQuitNum(Integer actionId, String userName);

    @Select("select * from action_user_view where id = #{id}")
    List<ActionUserView> listActionUserView(@Param("id") Integer id);
}
