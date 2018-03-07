package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;

import lombok.Data;
@Data
public class LoginRequest extends OperationRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8875265383735408067L;
	private String code;
	private WechatInfo wechat_userInfo;
	private SystemInfo system_info;

}
