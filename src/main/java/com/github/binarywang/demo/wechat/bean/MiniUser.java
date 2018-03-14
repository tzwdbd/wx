package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MiniUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private String nickName;
	private String openId;
	private String language;
	private String gender;
	private String city;
	private String province;
	private String country;
	private String avatarUrl;
	private Date gmtCreate;
	private Date gmtModified;
	private String weixinUnionid;
	private String wechatVersion;
	private String brand;
	private String model;
	private String screenWidth;
	private String screenHeight;
	private String system;
	private String platform;
	private String status;
	private String mail;

}
