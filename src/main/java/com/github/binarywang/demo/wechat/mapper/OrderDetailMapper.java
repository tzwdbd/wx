package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.binarywang.demo.wechat.bean.OrderDetail;
/**
 * @author liuxf
 */
public interface OrderDetailMapper {

	@Select("SELECT * FROM automan.order_detail WHERE order_no = #{orderNo}")
	List<OrderDetail> getOrderDetailList(@Param("orderNo") String orderNo);
	
	@Select("SELECT * FROM automan.order_detail WHERE order_no = #{orderNo} and product_entity_id = #{productEntityId} limit 1")
	OrderDetail getOrderDetailByOrderNoAndSkuId(@Param("orderNo") String orderNo,@Param("productEntityId") Long productEntityId);
	
	@Select("SELECT * FROM automan.order_detail WHERE express_no = #{expressNo}")
	List<OrderDetail> getOrderDetailByExpressList(@Param("expressNo") String expressNo);
	
}
