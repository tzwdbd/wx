package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MiniForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private Long miniUserId;
	private String formId;
	private Integer status;
	private Date gmtCreate;
	private Date gmtModified;

}
