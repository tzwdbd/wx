package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class OrderDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2745941150472250753L;
	private Long id;
    private String orderNo;
    private Long productId;
    private Long productEntityId;
    private String productUrl;
    private String productRebateUrl;
    private String productSku;
    private String siteName;
    private Integer num;
    private String myPrice;
    private String rmbPrice;
    private Integer status;
    private String mallOrderNo;
    private String expressCompany;
    private String expressNo;
    private Integer accountId;
    private Integer deviceId;
    private String isManual;
    private String manualMan;
    private Date gmtCreate;
    private Date gmtModified;
    private Date orderTime;
    private String totalPrice;
    private String singlePrice;
    private String isStockpile;
    private String remarks;
    private String units;
    private Integer promotionStatus;
    private Long company;
    private String promotionCodeList;
    private String promotionCodeListStatus;
    private Integer expressStatus;
    private String mallExpressFee;
    private String promotionFee;
    private Integer isAutoExpress;
    private String orderImg;
    private String isDirect;
    private Integer groupNumber;
    private Date dispatchTime;
    private  String totalPromotion;
    private  String totalPay;
    private  Long fromId;

}
