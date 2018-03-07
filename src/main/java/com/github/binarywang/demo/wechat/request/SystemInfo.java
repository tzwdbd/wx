package com.github.binarywang.demo.wechat.request;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@Data
public class SystemInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5625233244887208609L;
	private String wechat_version;
	private String brand;
	private String model;
	private String screen_width;
	private String screen_height;
	private String system;
	private String platform;

}
