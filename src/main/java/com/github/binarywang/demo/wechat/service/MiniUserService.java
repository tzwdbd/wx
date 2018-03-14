package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.request.UserInfo;
/**
 * @author liuxf
 */
public interface MiniUserService {
	
	MiniUser getUserById(Long id);

	public List<MiniUser> getUserList(MiniUser user);

	public int add(MiniUser user);

	public int update(Integer id, MiniUser user);

	public MiniUser addUser(UserInfo userInfo,SystemInfo systemInfo);
	
	public int updateMail(Long id, String mail);
	
	MiniUser getUserByOpenId(String openId);

}
