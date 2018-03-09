package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.github.binarywang.demo.wechat.bean.MallDefinition;
/**
 * @author liuxf
 */
public interface MallDefinitionMapper {

	@Select("SELECT * FROM oversea.mall_definition WHERE id = #{id}")
	MallDefinition getMallDefinitionById(Long id);
	
	@Select("SELECT * FROM oversea.mall_definition WHERE id in (${ids})")
	public List<MallDefinition> getMallDefinitionList(String ids);
	
	@Select("SELECT * FROM oversea.mall_definition WHERE name = #{name} limit 1")
	MallDefinition getMallDefinitionByName(String name);
}
