package com.github.binarywang.demo.wechat.mapper;

import java.util.Date;
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
	List<MiniOrder> getMiniOrderList(@Param("status")  Integer status);
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId} order by id desc limit #{pageSize}")
	List<MiniOrder> getAllMiniOrderList(@Param("miniUserId") Long miniUserId,@Param("pageSize") int pageSize);
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId} and site_name = #{siteName}  order by id desc limit #{pageSize}")
	List<MiniOrder> getAllMiniOrderBySiteNameList(@Param("miniUserId") Long miniUserId,@Param("siteName") String siteName,@Param("pageSize") int pageSize);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderByUserIdList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("pageSize") int pageSize);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and site_name = #{siteName} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderBySiteNameList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("siteName") String siteName,@Param("pageSize") int pageSize);

	@Update("UPDATE oversea.mini_order SET status = #{status}  WHERE id = #{id}")
	public int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
	@Update("UPDATE oversea.mini_order SET status = #{toStatus}  WHERE order_no = #{orderNo} and status =#{fromStatus}")
	public int updateMiniOrderByOrder(@Param("orderNo") String orderNo, @Param("fromStatus") Integer fromStatus,@Param("toStatus") Integer toStatus);
	
	@Select("SELECT * FROM oversea.mini_order WHERE order_no = #{orderNo} and product_entity_id = #{productEntityId} and mini_user_id=#{userId} limit 1")
	public MiniOrder getOrderDetailByOrderNoAndSkuId(@Param("orderNo")String orderNo,@Param("productEntityId") Long productEntityId,@Param("userId") Long userId) ;
	
	@Update("UPDATE oversea.mini_order SET status = #{toStatus}  WHERE id = #{id} and status =#{fromStatus}")
	public int updateMiniOrderById(@Param("id") Long id, @Param("fromStatus") Integer fromStatus,@Param("toStatus") Integer toStatus);
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getAllMiniOrderByTimeList(@Param("miniUserId") Long miniUserId,@Param("pageSize") int pageSize,@Param("time") Date time);
	
	@Select("SELECT * FROM oversea.mini_order WHERE mini_user_id = #{miniUserId} and site_name = #{siteName} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getAllMiniOrderBySiteNameAndTimeList(@Param("miniUserId") Long miniUserId,@Param("siteName") String siteName,@Param("pageSize") int pageSize,@Param("time") Date time);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderByUserIdAndTimeList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("pageSize") int pageSize,@Param("time") Date time);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and site_name = #{siteName} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderBySiteNameAndTimeList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("siteName") String siteName,@Param("pageSize") int pageSize,@Param("time") Date time);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderByUserIdAndTimesList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("pageSize") int pageSize,@Param("time") Date time);
	
	@Select("SELECT * FROM oversea.mini_order WHERE status = #{status} and mini_user_id = #{miniUserId} and site_name = #{siteName} and gmt_create<#{time} order by id desc limit #{pageSize}")
	List<MiniOrder> getMiniOrderBySiteNameAndTimesList(@Param("status") Integer status,@Param("miniUserId") Long miniUserId,@Param("siteName") String siteName,@Param("pageSize") int pageSize,@Param("time") Date time);
}
