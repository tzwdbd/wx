package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class IncomeInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String amount;
	private String can_presented;
	private String already_presented;
	private String deduct;
	private String expect_presented;

}
