package com.github.binarywang.demo.wechat.service;

import java.util.Date;
import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
/**
 * @author liuxf
 */
public interface MiniOrderService {
	
	List<MiniOrder> getMiniOrderList(Integer type,String siteName,Long userId,Date time,Integer pageSize);

	public int updateStatus(Long id, Integer status);
	
	public int updateMiniOrderByOrder(String orderNO, Integer fromStatus,Integer toStatus);
	
	MiniOrder getOrderDetailByOrderNoAndSkuId(String orderNo,Long productEntityId,Long userId);
	
	public int updateMiniOrderById(Long id, Integer fromStatus,Integer toStatus);
}
