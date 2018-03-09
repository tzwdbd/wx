package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
/**
 * @author liuxf
 */
public interface AccountMapper {

	@Select("SELECT * FROM automan.order_account_new WHERE account_id = #{accoutId}")
	OrderAccount getAccoutIdByAccoutId(Integer accoutId);

	@Insert("insert into automan.order_account_new(account_type,pay_account, login_pwd,status) values(#{accountType},#{payAccount}, #{loginPwd}, #{status})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(OrderAccount orderAccount);

	@Select("SELECT * FROM automan.order_account_new WHERE account_source = #{accountSource} and account_type = #{accountType}")
	List<OrderAccount> getOrderAccountByAccountSource(String accountSource, String accountType);
	
	@Update("UPDATE automan.order_account_new SET pay_account = #{orderAccount.payAccount} , login_pwd = #{orderAccount.loginPwd} WHERE account_id = #{orderAccount.accoutId}")
	public int updateOrderAccount(OrderAccount orderAccount);
	
	@Select("SELECT * FROM automan.order_account_new WHERE account_source = #{accountSource}")
	List<OrderAccount> getAllOrderAccountByAccountSource(String accountSource);
}
