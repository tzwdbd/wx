package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.OrderDetail;
/**
 * @author liuxf
 */
public interface OrderDetailService {
	
	OrderDetail getOrderDetailByOrderNoAndSkuId(String orderNo,Long productEntityId);
	
	List<OrderDetail> getOrderDetailList(String orderNo);
	
	List<OrderDetail> getOrderDetailByExpressList(String expressNo);
	
	String getStatus(OrderDetail orderDetail,Long userId);
	String getStatus(List<OrderDetail> orderDetails,Long userId);
	
	void sendMsg(String openId,String formId,String name);

}
