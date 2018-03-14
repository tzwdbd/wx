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

import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.provider.SqlProvider;
/**
 * @author liuxf
 */
public interface MiniUserMapper {

	@Select("SELECT * FROM oversea.mini_users WHERE id = #{id}")
	MiniUser getUserById(@Param("id")Long id);

	@SelectProvider(type=SqlProvider.class,method="selectUser")  
	public List<MiniUser> getUserList(MiniUser user);
	
	@Insert("insert into oversea.mini_users(nick_name, status,open_id,language,gender,city,province,country,avatar_url,gmt_create,gmt_modified,weixin_unionid,wechat_version,brand,model,screen_width,screen_height,system,platform,mail) values(#{nickName}, #{status},#{openId},#{language},#{gender},#{city},#{province},#{country},#{avatarUrl},now(),now(),#{weixinUnionid},#{wechatVersion},#{brand},#{model},#{screenWidth},#{screenHeight},#{system},#{platform},#{mail})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public int add(MiniUser user);

	@Update("UPDATE oversea.mini_users SET username = #{user.username} , age = #{user.age} WHERE id = #{id}")
	public int update(@Param("id") Integer id, @Param("user") MiniUser user);
	
	@Update("UPDATE oversea.mini_users SET mail = #{mail} WHERE id = #{id}")
	public int updateMail(@Param("id") Long id, @Param("mail") String mail);
	
	@Select("SELECT * FROM oversea.mini_users WHERE open_id = #{openId} limit 1")
	MiniUser getUserByOpenId(@Param("openId")String openId);
}
