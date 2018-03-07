package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;

import lombok.Data;
@Data
public class IndexResponse extends CommonResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7562677538535511735L;
	private IncomeInfo income;
	private String unauth_num;
}
