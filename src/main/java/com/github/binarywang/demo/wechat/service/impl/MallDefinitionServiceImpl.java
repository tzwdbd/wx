package com.github.binarywang.demo.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MallDefinition;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
import com.github.binarywang.demo.wechat.mapper.AccountMapper;
import com.github.binarywang.demo.wechat.mapper.MallDefinitionMapper;
import com.github.binarywang.demo.wechat.service.MallDefinitionService;
import com.github.binarywang.demo.wechat.service.OrderAccountService;
/**
 * @author liuxf
 */
@Service
public class MallDefinitionServiceImpl implements MallDefinitionService {
	
	@Autowired
	private MallDefinitionMapper mallDefinitionMapper;

	@Override
	public MallDefinition getMallDefinitionById(Long id) {
		return mallDefinitionMapper.getMallDefinitionById(id);
	}

	@Override
	public List<MallDefinition> getMallDefinitionList(String ids) {
		return mallDefinitionMapper.getMallDefinitionList(ids);
	}

	@Override
	public MallDefinition getMallDefinitionByName(String name) {
		return mallDefinitionMapper.getMallDefinitionByName(name);
	}



}
