package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
/**
 * @author liuxf
 */
public interface MiniOrderService {
	
	List<MiniOrder> getMiniOrderList(Integer status);

	public int updateStatus(Long id, Integer status);
}
