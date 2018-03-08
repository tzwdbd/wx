package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
/**
 * @author liuxf
 */
public interface MiniOrderMapper {

	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status}")
	List<MiniOrder> getMiniOrderList(Integer status);


	@Update("UPDATE oversea.mini_order SET status = #{status}  WHERE id = #{id}")
	public int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
}
