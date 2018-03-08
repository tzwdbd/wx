package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;

import lombok.Data;
@Data
public class UseAccountRequest extends OperationRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409116573462876126L;
	private String account_id;
	private String status;

}
