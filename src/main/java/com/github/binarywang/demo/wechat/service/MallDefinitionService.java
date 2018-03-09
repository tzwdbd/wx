package com.github.binarywang.demo.wechat.service;

import java.util.List;

import com.github.binarywang.demo.wechat.bean.MallDefinition;
/**
 * @author liuxf
 */
public interface MallDefinitionService {
	
	MallDefinition getMallDefinitionById(Long id);

	List<MallDefinition> getMallDefinitionList(String ids);
	
	MallDefinition getMallDefinitionByName(String name);

}
