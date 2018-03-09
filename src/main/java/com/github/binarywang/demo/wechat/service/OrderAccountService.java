package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.OrderAccount;
/**
 * @author liuxf
 */
public interface OrderAccountService {
	
	OrderAccount getOrderAccountByAccountId(Integer accountId);

	public int add(OrderAccount orderAccount);
	
	List<OrderAccount> getOrderAccountByAccountSource(String accountSource,String accountType);

	public int updateOrderAccount(OrderAccount orderAccount);
	
	List<OrderAccount> getAOrderAccountByAccountSource(String accountSource);
}
