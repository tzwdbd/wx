package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.request.SystemInfo;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
public interface MiniIncomeService {
	
	MiniIncome getMiniIncomeByUserId(Long userId);

	public int add(MiniIncome miniIncome);

	public int update(Integer id, MiniUser user);

	public int delete(Integer id);
	
	public MiniIncome addUser(WxMaUserInfo userInfo,SystemInfo systemInfo);

}
