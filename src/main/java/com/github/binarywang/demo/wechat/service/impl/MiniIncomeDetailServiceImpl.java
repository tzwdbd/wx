package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniIncomeDetail;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeDetailMapper;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeMapper;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.service.MiniIncomeDetailService;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class MiniIncomeDetailServiceImpl implements MiniIncomeDetailService {
	

	@Autowired
	private MiniIncomeDetailMapper miniIncomeDetailMapper;

	@Override
	public List<MiniIncomeDetail> getMiniIncomeDetailByUserId(Long userId,int pageNo,int pageSize) {
		return miniIncomeDetailMapper.getMiniIncomeDetailByUserId(userId,(pageNo-1)*pageSize,pageSize);
	}

	@Override
	public int add(MiniIncomeDetail miniIncomeDetail) {
		return miniIncomeDetailMapper.add(miniIncomeDetail);
	}

	

}
