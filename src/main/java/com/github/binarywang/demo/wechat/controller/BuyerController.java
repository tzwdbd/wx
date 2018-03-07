package com.github.binarywang.demo.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.exception.ProcessStatusCode;
import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.OperationRequest;
import com.github.binarywang.demo.wechat.request.UserInfo;
import com.github.binarywang.demo.wechat.response.IncomeInfo;
import com.github.binarywang.demo.wechat.response.IndexResponse;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;
import com.github.binarywang.demo.wechat.service.MiniUserService;
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
    private WxMaService wxService;
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
    public String login(String code,@RequestBody BuyerAccount buyerAccount) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }
        
        try {
            WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo("11");
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            this.logger.info(session.getExpiresin().toString());
            //保存用户
//    			User user = new User();
//    			user.setId(1);
//    			userService.add(user);
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }


}
