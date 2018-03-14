package com.github.binarywang.demo.wechat.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
/**
 * @author liuxf
 */
public interface UserTradeExpressMapper {

	@Select("SELECT status FROM oversea.user_trade_express WHERE order_no = #{orderNo} limit 1")
	Integer getUserTradeExpressByOrderNo(@Param("orderNo") String orderNo);
}
