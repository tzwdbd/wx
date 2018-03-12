package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class IncomeDetailInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String title;
	private String date;
	private String order_no;
	private String income;
	private String type;

}
