package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class AlipayTradeMoney implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private Integer type;
	private Long user_id;
	private String alipayAccount;
	private String realName;
	private Integer couponValue;
	private Integer status;
	private Integer operatorId;
	private Date gmtCreate;
	private Date gmtModified;

}
