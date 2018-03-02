package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.User;

public interface UserService {
	
	User getUserById(Long id);

	public List<User> getUserList();

	public int add(User user);

	public int update(Integer id, User user);

	public int delete(Integer id);

}
