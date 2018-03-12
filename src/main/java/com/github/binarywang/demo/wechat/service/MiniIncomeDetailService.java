package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniIncomeDetail;
/**
 * @author liuxf
 */
public interface MiniIncomeDetailService {
	
	List<MiniIncomeDetail> getMiniIncomeDetailByUserId(Long userId,int pageNo,int pageSize);

	public int add(MiniIncomeDetail miniIncomeDetail);


}
