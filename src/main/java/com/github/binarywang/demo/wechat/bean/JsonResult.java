package com.github.binarywang.demo.wechat.bean;

public class JsonResult {
	
	private String status = null;

	private Object result = null;

	public JsonResult status(String status) {
		this.status = status;
		return this;
	}

}
