package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;

import lombok.Data;
@Data
public class ApplyCashRequest extends OperationRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409116573462876126L;
	private String cash_amount;
	private String type;
	private String alipay;
	private String really_name;

}
