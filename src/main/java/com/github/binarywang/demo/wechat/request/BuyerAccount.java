package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;

import lombok.Data;
@Data
public class BuyerAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409116573462876126L;
	private Mall mall;
	private String account;
	private String password;
	private String account_id;
	private String mail;
	private String status;

}
