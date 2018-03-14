package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;

import lombok.Data;
@Data
public class OrderListRequest extends OperationRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8409116573462876126L;
	private String type;
	private String mall_id;
	private String create_time;
	private String page_size;

}
