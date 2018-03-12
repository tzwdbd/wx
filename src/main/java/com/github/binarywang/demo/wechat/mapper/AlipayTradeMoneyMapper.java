package com.github.binarywang.demo.wechat.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import com.github.binarywang.demo.wechat.bean.AlipayTradeMoney;
/**
 * @author liuxf
 */
public interface AlipayTradeMoneyMapper {


	@Insert("insert into oversea.alipay_trade_money(type, user_id,alipay_account,real_name,coupon_value,status,gmt_create,gmt_modified) values(#{type}, #{userId}, #{alipayAccount}, #{realName},#{couponValue},0, now(),now()")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(AlipayTradeMoney alipayTradeMoney);

}
