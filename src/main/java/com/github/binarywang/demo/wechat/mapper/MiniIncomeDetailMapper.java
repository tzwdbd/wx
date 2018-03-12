package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MiniIncomeDetail;
/**
 * @author liuxf
 */
public interface MiniIncomeDetailMapper {

	@Select("SELECT * FROM oversea.mini_income_detail WHERE mini_user_id = #{miniUserId} and status =1 limit #{pageNo},#{pageSize}")
	List<MiniIncomeDetail> getMiniIncomeDetailByUserId(@Param("miniUserId")  Long userId,int pageNo,int pageSize);

	@Insert("insert into oversea.mini_income_detail(mini_user_id, order_no,type,income,title,gmt_create,gmt_modified,status) values(#{miniUserId}, #{orderNo}, #{type}, #{income},#{title}, now(),now(),0")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(MiniIncomeDetail miniIncomeDetail);

	@Update("UPDATE oversea.mini_income_detail SET expect_presented = expect_presented+#{expectPresented} WHERE mini_user_id = #{miniUserId}")
	public int updateExpectPresented(@Param("miniUserId") Long miniUserId,Integer expectPresented);


}
