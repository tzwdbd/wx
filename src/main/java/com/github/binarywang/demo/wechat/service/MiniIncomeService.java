package com.github.binarywang.demo.wechat.service;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
/**
 * @author liuxf
 */
public interface MiniIncomeService {
	
	MiniIncome getMiniIncomeByUserId(Long userId);

	public int add(MiniIncome miniIncome);

	public int updateExpectPresented(Long miniUserId,Integer expectPresented);

}
