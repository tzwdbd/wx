package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.Mall;

import lombok.Data;
@Data
public class OrderListResponse extends CommonResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private List<OrderInfo> order_list;
	private List<Mall> mall_list;

}
