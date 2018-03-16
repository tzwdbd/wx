package com.github.binarywang.demo.wechat.response;

import java.io.Serializable;
import java.util.List;

import com.github.binarywang.demo.wechat.request.Mall;

import lombok.Data;
@Data
public class GetCreateResponse extends CommonResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7562677538535511735L;
	private List<Mall> mall_list;
	private String agreement_title;
	private List<Agreement> agreement_list;
}
