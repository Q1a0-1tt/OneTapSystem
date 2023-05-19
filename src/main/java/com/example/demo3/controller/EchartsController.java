package com.example.demo3.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.example.demo3.Mapper.LeaveMapper;
import com.example.demo3.common.Result;
import com.example.demo3.entity.Leave;
import com.example.demo3.entity.User;
import com.example.demo3.service.ILeaveService;
import com.example.demo3.service.IUserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ILeaveService leaveService;

    @Autowired
    private LeaveMapper leaveMapper;

    @GetMapping("/example")
    public Result get(){
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result member(){
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for(User user : list){
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }

    @GetMapping("/leaveCount")
    public Result countLeavesByStatus() {
        List<Leave> leaves = leaveService.list();
        int[] counts = new int[3];
        for (Leave leave : leaves) {
            int status = leave.getStatus();
            counts[status]++;
        }
        String[] statusNames = new String[] {"待审核", "审核通过", "审核失败"};
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < statusNames.length; i++) {
            Map<String, Object> dataItem = new HashMap<>();
            dataItem.put("name", statusNames[i]);
            dataItem.put("value", counts[i]);
            dataList.add(dataItem);
        }
        return Result.success(dataList);
    }


}
