package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.User;
import com.github.binarywang.demo.wechat.mapper.UserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.service.UserService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	@Override
	public List<User> getUserList(User user) {
		return userMapper.getUserList(user);
	}

	@Override
	public int add(User user) {
		return userMapper.add(user);
	}

	@Override
	public int update(Integer id, User user) {
		return userMapper.update(id, user);
	}

	@Override
	public int delete(Integer id) {
		return userMapper.delete(id);
	}

	@Override
	public User addUser(WxMaUserInfo userInfo, SystemInfo systemInfo) {
		//保存用户
		User user = new User();
		user.setAvatarUrl(userInfo.getAvatarUrl());
		user.setBrand(systemInfo.getBrand());
		user.setCity(userInfo.getCity());
		user.setCountry(userInfo.getCountry());
		user.setGender(userInfo.getGender());
		user.setGmtCreate(new Date());
		user.setGmtMmodified(new Date());
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

}
