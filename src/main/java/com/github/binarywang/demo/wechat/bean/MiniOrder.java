package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class MiniOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
	private Long miniUserId;
	private String orderNo;
	private String siteName;
	private Integer status;
	private Long productId;
	private Long productEntityId;
	private String modeType;
	private Integer accountId;
	private Integer deviceId;
	private String remarks;
	private Long company;
	private Date gmtCreate;
	private Date gmtModified;

}
