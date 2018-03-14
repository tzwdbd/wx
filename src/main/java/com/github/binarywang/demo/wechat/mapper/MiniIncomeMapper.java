package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.provider.SqlProvider;
/**
 * @author liuxf
 */
public interface MiniIncomeMapper {

	@Select("SELECT * FROM oversea.mini_income WHERE mini_user_id = #{miniUserId} limit 1")
	MiniIncome getMiniIncomeByUserId(@Param("miniUserId")  Long userId);

	@Insert("insert into oversea.mini_income(mini_user_id, expect_presented,can_presented,already_presented,deduct,gmt_create,gmt_modified) values(#{miniIncome.miniUserId}, 0, 0, 0,0, now(),now())")
	@Options(useGeneratedKeys=true,keyProperty="miniIncome.id")
	public int add(@Param("miniIncome") MiniIncome miniIncome);

	@Update("UPDATE oversea.mini_income SET expect_presented = expect_presented+#{expectPresented} WHERE mini_user_id = #{miniUserId}")
	public int updateExpectPresented(@Param("miniUserId") Long miniUserId,@Param("expectPresented")  Integer expectPresented);
	
	@Update("UPDATE oversea.mini_income SET can_presented = can_presented-#{canPresented} WHERE mini_user_id = #{miniUserId} and can_presented>=#{canPresented}")
	public int updateCanPresented(@Param("miniUserId") Long miniUserId,@Param("canPresented") Integer canPresented);
	
	@Update("UPDATE oversea.mini_income SET already_presented = already_presented+#{alreadyPresented} WHERE mini_user_id = #{miniUserId}")
	public int updateAlreadyPresented(@Param("miniUserId") Long miniUserId,@Param("alreadyPresented") Integer alreadyPresented);


}
