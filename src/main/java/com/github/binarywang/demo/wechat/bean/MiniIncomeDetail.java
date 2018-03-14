package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MiniIncomeDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private Long miniUserId;
	private String order_no;
	private Integer type;
	private Integer status;
	private String income;
	private String title;
	private String paymentNo;
	private Date payment_time;
	private String userName;
	private Date gmtCreate;
	private Date gmtModified;

}
