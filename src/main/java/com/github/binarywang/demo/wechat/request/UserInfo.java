package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String nickName;
	private String openId;
	private String language;
	private String gender;
	private String city;
	private String province;
	private String country;
	private String avatarUrl;
	private String unionId;

}
