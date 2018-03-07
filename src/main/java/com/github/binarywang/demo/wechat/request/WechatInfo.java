package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class WechatInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String rawData;
	private String signature;
	private String encryptedData;
	private String iv;
	private UserInfo user_info;
}
