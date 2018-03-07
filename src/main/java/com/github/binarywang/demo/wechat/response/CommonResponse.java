package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;

import lombok.Data;
@Data
public class CommonResponse implements Serializable{
	private String status;
	private String user_id;
	private String haihu_session;
	private String nick_name;
}
