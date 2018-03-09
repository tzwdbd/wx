package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class BuyerGoods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String img;
	private String mall_price;
	private String title;
	private String number;
	private String seq;
	private String seq_desc;
	private String status;

}
