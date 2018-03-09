package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.github.binarywang.demo.wechat.request.Mall;

import lombok.Data;
@Data
public class OrderInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private Mall mall;
	private String status;
	private String income;
	private String total_price;
	private String rmb_price;
	private String voucher;
	private List<BuyerGoods> goods_list;
	private List<BuyerPackage> package_list;
	private String create_time;
	private String end_time;
	private String order_no;
	private String mall_no;
	private String order_time;
	

}
