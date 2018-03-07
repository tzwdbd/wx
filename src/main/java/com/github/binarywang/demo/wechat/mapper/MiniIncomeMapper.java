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
	MiniIncome getMiniIncomeByUserId(Long userId);

	@Insert("insert into oversea.mini_income(mini_user_id, expect_presented) values(#{miniUserId}, #{expectPSresented})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(MiniIncome miniIncome);

	@Update("UPDATE oversea.mini_income SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") MiniUser user);

	@Delete("DELETE from oversea.mini_income where id = #{id} ")
	public int delete(Integer id);

}
