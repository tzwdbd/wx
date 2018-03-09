package com.github.binarywang.demo.wechat.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author liuxf
 */
public interface ProductMapper {

	@Select("SELECT * FROM oversea.product WHERE id = #{productId}")
	String getProductName(@Param("productId") Long productId);
	
	@Select("SELECT * FROM oversea.product_image WHERE product_id = #{productId} limit 1")
	String getProductImg(@Param("productId") Long productId);
	
}
