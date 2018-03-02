package com.github.binarywang.demo.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.User;
import com.github.binarywang.demo.wechat.mapper.UserMapper;
import com.github.binarywang.demo.wechat.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Long id) {
		return userMapper.getUserById(id);
	}

	@Override
	public List<User> getUserList() {
		return userMapper.getUserList();
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

}
