package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeMapper;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class MiniIncomeServiceImpl implements MiniIncomeService {
	

	@Autowired
	private MiniIncomeMapper miniIncomeMapper;

	@Override
	public MiniIncome getMiniIncomeByUserId(Long userId) {
		return miniIncomeMapper.getMiniIncomeByUserId(userId);
	}

	@Override
	public int add(MiniIncome miniIncome) {
		return miniIncomeMapper.add(miniIncome);
	}

	@Override
	public int update(Integer id, MiniUser user) {
		return miniIncomeMapper.update(id, user);
	}

	@Override
	public int delete(Integer id) {
		return miniIncomeMapper.delete(id);
	}

	@Override
	public MiniIncome addUser(WxMaUserInfo userInfo, SystemInfo systemInfo) {
		//保存用户
		MiniIncome miniIncome = new MiniIncome();
		return miniIncome;
	}

}