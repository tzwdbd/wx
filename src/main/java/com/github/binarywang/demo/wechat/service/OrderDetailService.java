package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.OrderDetail;
/**
 * @author liuxf
 */
public interface OrderDetailService {
	
	OrderDetail getOrderDetailByOrderNoAndSkuId(String orderNo,Long productEntityId);
	
	List<OrderDetail> getOrderDetailList(String orderNo);

}
