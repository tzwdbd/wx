package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.User;

public interface UserMapper {

	@Select("SELECT * FROM oversea.users WHERE id = #{id}")
	User getUserById(Long id);

	@Select("SELECT * FROM oversea.users")
	public List<User> getUserList();

	@Insert("insert into oversea.users(username, age, ctm) values(#{username}, #{age}, now())")
	public int add(User user);

	@Update("UPDATE oversea.users SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") User user);

	@Delete("DELETE from oversea.users where id = #{id} ")
	public int delete(Integer id);
}
