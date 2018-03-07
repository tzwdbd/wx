package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.User;
import com.github.binarywang.demo.wechat.request.SystemInfo;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
public interface UserService {
	
	User getUserById(Long id);

	public List<User> getUserList(User user);

	public int add(User user);

	public int update(Integer id, User user);

	public int delete(Integer id);
	
	public User addUser(WxMaUserInfo userInfo,SystemInfo systemInfo);

}
