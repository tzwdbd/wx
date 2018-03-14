package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;

import com.github.binarywang.demo.wechat.request.UserInfo;

import lombok.Data;
@Data
public class LoginResponse extends CommonResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7562677538535511735L;
	private String haihu_session;
	private UserInfo user_info;
}
