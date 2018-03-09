package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
@Data
public class BuyerPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private List<BuyerGoods> goods_list;
	private String status;
	private String express_no;

}
