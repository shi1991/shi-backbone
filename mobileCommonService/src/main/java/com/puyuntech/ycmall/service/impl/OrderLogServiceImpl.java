package com.puyuntech.ycmall.service.impl;

import org.springframework.stereotype.Service;

import com.puyuntech.ycmall.entity.OrderLog;
import com.puyuntech.ycmall.service.OrderLogService;

@Service("orderLogServiceImpl")
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, Long> implements OrderLogService{

}
