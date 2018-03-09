package com.github.binarywang.demo.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.OrderDetail;
import com.github.binarywang.demo.wechat.mapper.OrderDetailMapper;
import com.github.binarywang.demo.wechat.service.OrderDetailService;
/**
 * @author liuxf
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;

	@Override
	public OrderDetail getOrderDetailByOrderNoAndSkuId(String orderNo, Long productEntityId) {
		return orderDetailMapper.getOrderDetailByOrderNoAndSkuId(orderNo, productEntityId);
	}

	@Override
	public List<OrderDetail> getOrderDetailList(String orderNo) {
		return orderDetailMapper.getOrderDetailList(orderNo);
	}



}
