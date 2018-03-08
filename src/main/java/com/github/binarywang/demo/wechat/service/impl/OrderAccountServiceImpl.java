package com.github.binarywang.demo.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.OrderAccount;
import com.github.binarywang.demo.wechat.mapper.AccountMapper;
import com.github.binarywang.demo.wechat.service.OrderAccountService;
/**
 * @author liuxf
 */
@Service
public class OrderAccountServiceImpl implements OrderAccountService {
	
	@Autowired
	private AccountMapper accountMapper;

	@Override
	public OrderAccount getOrderAccountByAccountId(Integer accountId) {
		return accountMapper.getAccoutIdByAccoutId(accountId);
	}

	@Override
	public int add(OrderAccount orderAccount) {
		return accountMapper.add(orderAccount);
	}


}
