package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;
@Data
public class LoginResponse extends CommonResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7562677538535511735L;
	private String haihu_session;
	private WxMaUserInfo user_info;
}
