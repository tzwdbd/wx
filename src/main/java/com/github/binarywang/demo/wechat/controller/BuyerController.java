package com.github.binarywang.demo.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
import com.github.binarywang.demo.wechat.exception.ProcessStatusCode;
import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.Mall;
import com.github.binarywang.demo.wechat.request.OperationRequest;
import com.github.binarywang.demo.wechat.request.UserInfo;
import com.github.binarywang.demo.wechat.response.AccountResponse;
import com.github.binarywang.demo.wechat.response.GetCreateResponse;
import com.github.binarywang.demo.wechat.response.IncomeInfo;
import com.github.binarywang.demo.wechat.response.IndexResponse;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;
import com.github.binarywang.demo.wechat.service.MiniUserService;
import com.github.binarywang.demo.wechat.service.OrderAccountService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
import com.github.binarywang.demo.wechat.utils.ThreeDES;

import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

/**
 * 账号处理
 *
 * @author liuxf
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderAccountService orderAccountService;
    @Autowired
	private MiniIncomeService miniIncomeService;

    /**
     * 首页
     */
    @PostMapping("buyerIndex")
    public String index(@RequestBody OperationRequest operationRequest) {
        if (StringUtils.isBlank(operationRequest.getHaihu_session())) {
            return "empty session";
        }
        this.logger.info(operationRequest.getHaihu_session());
        Long userId = Long.parseLong(ThreeDES.decryptMode(operationRequest.getHaihu_session()));
        MiniIncome miniIncome = miniIncomeService.getMiniIncomeByUserId(userId);
        IndexResponse indexResponse = new IndexResponse();
        	indexResponse.setHaihu_session(operationRequest.getHaihu_session());
        	indexResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        	indexResponse.setUser_id(String.valueOf(userId));
        	IncomeInfo income = new IncomeInfo();
        	income.setCan_presented(String.valueOf(miniIncome.getCanPresented()));
        	income.setAlready_presented(String.valueOf(miniIncome.getAlreadyPresented()));
        	income.setDeduct(String.valueOf(miniIncome.getDeduct()));
        	income.setExpect_presented(String.valueOf(miniIncome.getExpectPresented()));
        	income.setAmount(String.valueOf(miniIncome.getAlreadyPresented()+miniIncome.getCanPresented()+miniIncome.getExpectPresented()-miniIncome.getDeduct()));
        	indexResponse.setIncome(income);
        	indexResponse.setUnauth_num("2");
        return JsonUtils.toJson(indexResponse);
    }
    
    /**
     * 添加账号
     */
    @PostMapping("addAccount")
    public String login(@RequestBody BuyerAccount buyerAccount) {
        if (StringUtils.isBlank(buyerAccount.getHaihu_session())) {
            return "empty session";
        }
        Mall mall = buyerAccount.getMall();
        Long userId = Long.parseLong(ThreeDES.decryptMode(buyerAccount.getHaihu_session()));
        
        OrderAccount orderAccount = new OrderAccount();
        orderAccount.setAccountType(mall.getMall());
        orderAccount.setPayAccount(buyerAccount.getAccount());
        orderAccount.setStatus(20);
        try {
			orderAccount.setLoginPwd(ThreeDES.encryptMode(buyerAccount.getPassword().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        orderAccountService.add(orderAccount);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setHaihu_session(buyerAccount.getHaihu_session());
        accountResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        accountResponse.setUser_id(String.valueOf(userId));
        accountResponse.setAccount_id(String.valueOf(orderAccount.getAccountId()));
        return JsonUtils.toJson(accountResponse);
    }
    
    /**
     * 添加账号
     */
    @PostMapping("getCreate")
    public String getCreate(@RequestBody OperationRequest operationRequest) {
        if (StringUtils.isBlank(operationRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(operationRequest.getHaihu_session()));
        GetCreateResponse getCreateResponse = new GetCreateResponse();
        getCreateResponse.setHaihu_session(operationRequest.getHaihu_session());
        getCreateResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        getCreateResponse.setUser_id(String.valueOf(userId));
        
        return JsonUtils.toJson(getCreateResponse);
    }


}
