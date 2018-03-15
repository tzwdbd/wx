package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MiniForm;
/**
 * @author liuxf
 */
public interface MiniFormMapper {


	@Insert("insert into oversea.mini_form(mini_user_id, form_id,status,gmt_create,gmt_modified) values(#{miniForm.miniUserId}, #{miniForm.formId},#{miniForm.status}, now(),now())")
	public int add(@Param("miniForm") MiniForm miniForm);
	
	@Select("SELECT * FROM oversea.mini_form WHERE status = 0 and mini_user_id = #{miniUserId} limit 1")
	public MiniForm getMiniForm(@Param("miniUserId") Long miniUserId);
	
	@Update("UPDATE oversea.mini_form SET status = 1 WHERE id = #{id}")
	public int update(@Param("id") Long id);

}
