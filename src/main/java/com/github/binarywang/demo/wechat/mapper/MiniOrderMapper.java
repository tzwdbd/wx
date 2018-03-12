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
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId}")
	List<MiniOrder> getAllMiniOrderList(Long miniUserId);
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId} and site_name = #{siteName}")
	List<MiniOrder> getAllMiniOrderBySiteNameList(Long miniUserId,String siteName);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId}")
	List<MiniOrder> getMiniOrderByUserIdList(Integer status,Long miniUserId);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and site_name = #{siteName}")
	List<MiniOrder> getMiniOrderBySiteNameList(Integer status,Long miniUserId,String siteName);

	@Update("UPDATE oversea.mini_order SET status = #{status}  WHERE id = #{id}")
	public int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
	@Update("UPDATE oversea.mini_order SET status = #{toStatus}  WHERE order_no = #{orderNo} and status =#{fromStatus}")
	public int updateMiniOrderByOrder(@Param("orderNo") String orderNo, @Param("fromStatus") Integer fromStatus,@Param("toStatus") Integer toStatus);
	
}
