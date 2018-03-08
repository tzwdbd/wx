package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MallDefinition;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
/**
 * @author liuxf
 */
public interface MallDefinitionMapper {

	@Select("SELECT * FROM oversea.mall_definition WHERE id = #{id}")
	MallDefinition getMallDefinitionById(Long id);
	
	@Select("SELECT * FROM oversea.mall_definition WHERE id in (${ids})")
	public List<MallDefinition> getMallDefinitionList(String ids);
}
