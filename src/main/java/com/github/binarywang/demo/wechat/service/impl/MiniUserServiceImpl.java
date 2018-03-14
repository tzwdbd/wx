package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.request.UserInfo;
import com.github.binarywang.demo.wechat.service.MiniUserService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class MiniUserServiceImpl implements MiniUserService {
	
	@Autowired
	private MiniUserMapper userMapper;

	@Override
	public MiniUser getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	@Override
	public List<MiniUser> getUserList(MiniUser user) {
		return userMapper.getUserList(user);
	}

	@Override
	public int add(MiniUser user) {
		return userMapper.add(user);
	}

	@Override
	public int update(Integer id, MiniUser user) {
		return userMapper.update(id, user);
	}

	@Override
	public MiniUser addUser(UserInfo userInfo, SystemInfo systemInfo) {
		//保存用户
		MiniUser user = new MiniUser();
		user.setAvatarUrl(userInfo.getAvatarUrl());
		user.setBrand(systemInfo.getBrand());
		user.setCity(userInfo.getCity());
		user.setCountry(userInfo.getCountry());
		user.setGender(userInfo.getGender());
		user.setGmtCreate(new Date());
		user.setGmtModified(new Date());
		user.setLanguage(userInfo.getLanguage());
		user.setModel(systemInfo.getModel());
		user.setNickName(userInfo.getNickName());
		user.setOpenId(userInfo.getOpenId());
		user.setPlatform(systemInfo.getPlatform());
		user.setProvince(userInfo.getProvince());
		user.setScreenHeight(systemInfo.getScreen_height());
		user.setScreenWidth(systemInfo.getScreen_width());
		user.setSystem(systemInfo.getSystem());
		user.setWechatVersion(systemInfo.getWechat_version());
		add(user);
		return user;
	}

	@Override
	public int updateMail(Long id, String mail) {
		return userMapper.updateMail(id, mail);
	}

	@Override
	public MiniUser getUserByOpenId(String openId) {
		return userMapper.getUserByOpenId(openId);
	}

}
