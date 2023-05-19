package com.example.demo3.service.impl;

import com.example.demo3.entity.Message;
import com.example.demo3.mapper.MessageMapper;
import com.example.demo3.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李彤彤
 * @since 2023-04-05
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
