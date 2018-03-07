package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MiniIncome implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private Long miniUserId;
	private String canPresented;
	private String alreadyPresented;
	private String deduct;
	private String expectPresented;
	private Date gmtCreate;
	private Date gmtMmodified;

}
