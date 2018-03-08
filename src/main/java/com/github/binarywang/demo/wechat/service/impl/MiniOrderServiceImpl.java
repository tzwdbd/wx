package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.service.MiniOrderService;
import com.github.binarywang.demo.wechat.service.MiniUserService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class MiniOrderServiceImpl implements MiniOrderService {
	
	@Autowired
	private MiniOrderMapper miniOrderMapper;

	@Override
	public List<MiniOrder> getMiniOrderList(Integer status) {
		return miniOrderMapper.getMiniOrderList(status);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		return miniOrderMapper.updateStatus(id, status);
	}


}
