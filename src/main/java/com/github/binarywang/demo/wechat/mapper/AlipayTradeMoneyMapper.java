package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.AlipayTradeMoney;
/**
 * @author liuxf
 */
public interface AlipayTradeMoneyMapper {


	@Insert("insert into oversea.alipay_trade_money(type, user_id,alipay_account,real_name,coupon_value,status,gmt_create,gmt_modified) values(#{alipayTradeMoney.type}, #{alipayTradeMoney.userId}, #{alipayTradeMoney.alipayAccount}, #{alipayTradeMoney.realName},#{alipayTradeMoney.couponValue},0, now(),now())")
	public int add(@Param("alipayTradeMoney") AlipayTradeMoney alipayTradeMoney);
	
	@Select("SELECT * FROM oversea.alipay_trade_money WHERE status = 2")
	public List<AlipayTradeMoney> getAlipayTradeMoneyList();
	
	@Update("UPDATE oversea.alipay_trade_money SET status = 1 WHERE id = #{id}")
	public int update(@Param("id") Long id);

}
