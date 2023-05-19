package com.example.demo3.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo3.Mapper.LeaveMapper;
import com.example.demo3.entity.Leave;
import com.example.demo3.service.ILeaveService;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave> implements ILeaveService {
}
