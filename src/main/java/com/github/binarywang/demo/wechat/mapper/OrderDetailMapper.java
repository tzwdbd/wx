package com.github.binarywang.demo.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.github.binarywang.demo.wechat.bean.OrderDetail;
/**
 * @author liuxf
 */
public interface OrderDetailMapper {

	@Select("SELECT * FROM automan.order_detail WHERE order_no = #{orderNo}")
	List<OrderDetail> getOrderDetailList(String orderNo);
	
	@Select("SELECT * FROM automan.order_detail WHERE order_no = #{orderNo} and product_entity_id = #{productEntityId} limit 1")
	OrderDetail getOrderDetailByOrderNoAndSkuId(String orderNo,Long productEntityId);
	
}
