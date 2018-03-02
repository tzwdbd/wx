package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;

import lombok.Data;
@Data
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private String nick;

}
