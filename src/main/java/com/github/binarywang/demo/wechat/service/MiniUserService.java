package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.request.SystemInfo;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
public interface MiniUserService {
	
	MiniUser getUserById(Long id);

	public List<MiniUser> getUserList(MiniUser user);

	public int add(MiniUser user);

	public int update(Integer id, MiniUser user);

	public int delete(Integer id);
	
	public MiniUser addUser(WxMaUserInfo userInfo,SystemInfo systemInfo);

}
