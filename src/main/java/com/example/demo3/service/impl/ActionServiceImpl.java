package com.example.demo3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo3.Mapper.ActionMapper;
import com.example.demo3.entity.Action;
import com.example.demo3.entity.ActionUserView;
import com.example.demo3.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements IActionService {
    @Autowired
    private ActionMapper actionMapper;

    /**
     * 检查用户是否已经参加互动
     * @param actionId
     * @param userName
     * @return
     */
    @Override
    public int checkUser(Integer actionId, String userName) {
        return actionMapper.checkUser(actionId,userName);
    }
    /**
     * 参加活动
     * @param actionId
     * @param userName
     */
    @Override
    public void joinAction(Integer actionId, String userName) {
        actionMapper.updateJoinNum(actionId, userName);
    }

    /**
     * 退出活动
     * @param actionId
     * @param userName
     */
    @Override
    public void quitAction(Integer actionId, String userName) {
        actionMapper.updateQuitNum(actionId,userName);
    }

    public List<ActionUserView> listActionUserView(Integer id) {
        return actionMapper.listActionUserView(id);
    }
}

