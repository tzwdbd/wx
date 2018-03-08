package com.github.binarywang.demo.wechat.service;

import com.github.binarywang.demo.wechat.bean.OrderAccount;
/**
 * @author liuxf
 */
public interface OrderAccountService {
	
	OrderAccount getOrderAccountByAccountId(Integer accountId);

	public int add(OrderAccount orderAccount);


}
