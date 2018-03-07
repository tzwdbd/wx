package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class Mall implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5652726954451702537L;
	private String mall;
	private String mall_id;
	private String mall_icon;
	private String country;

}
