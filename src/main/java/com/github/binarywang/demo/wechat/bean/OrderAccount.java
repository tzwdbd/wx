package com.github.binarywang.demo.wechat.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * @author liuxf
 */
@Data
public class OrderAccount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7737349552018565682L;
	private Integer accountId;
    private String accountType;
    private String payAccount;
    private Long creditCardId;
    private String loginPwd;
    private String payPwd;
    private Long identityMobile;
    private String balanceWb;
    private String balance;
    private Integer volume;
    private Integer threshDaily;
    private Integer threshMonthly;
    private Integer status;
    private String remark;
    private Integer cntPayed;
    private Float amtPayed;
    private Date createTime;
    private Date modifyTime;
    private Integer deviceId;
    private String isPrime;
    private Long expressCompanyId;
    private Date effectiveTime;
    private String isTaxReturn;

}
