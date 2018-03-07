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

import com.github.binarywang.demo.wechat.bean.User;
import com.github.binarywang.demo.wechat.provider.SqlProvider;
/**
 * @author liuxf
 */
public interface UserMapper {

	@Select("SELECT * FROM oversea.mini_users WHERE id = #{id}")
	User getUserById(Long id);

	@SelectProvider(type=SqlProvider.class,method="selectUser")  
	public List<User> getUserList(User user);
	
	@Insert("insert into oversea.mini_users(nick_name, status) values(#{nickName}, #{status})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(User user);

	@Update("UPDATE oversea.mini_users SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") User user);

	@Delete("DELETE from oversea.mini_users where id = #{id} ")
	public int delete(Integer id);

}
