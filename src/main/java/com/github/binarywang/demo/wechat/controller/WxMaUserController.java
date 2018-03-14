package com.github.binarywang.demo.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.exception.ProcessStatusCode;
import com.github.binarywang.demo.wechat.request.LoginRequest;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.request.UserInfo;
import com.github.binarywang.demo.wechat.request.WechatInfo;
import com.github.binarywang.demo.wechat.response.LoginResponse;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;
import com.github.binarywang.demo.wechat.service.MiniUserService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
import com.github.binarywang.demo.wechat.utils.ThreeDES;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 微信小程序用户接口
 *
 * @author liuxf
 */
@RestController
@RequestMapping("/mp")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;
    @Autowired
	private MiniUserService userService;
    @Autowired
    private MiniIncomeService miniIncomeService;

    /**
     * 登陆接口
     */
    @PostMapping("mpLogin")
    public String login(@RequestBody LoginRequest loginRequest) {
        if (StringUtils.isBlank(loginRequest.getCode())) {
            return "empty jscode";
        }
        try {
            //WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(loginRequest.getCode());
//            this.logger.info(session.getOpenid());
//            this.logger.info(session.getExpiresin().toString());
//            this.logger.info(session.getUnionid());
            WechatInfo wechatInfo = loginRequest.getWechat_userInfo();
            SystemInfo systemInfo = loginRequest.getSystem_info();
            //this.logger.info("session:"+session.getSessionKey()+"-->rewdata:"+wechatInfo.getRawData()+"-->signature:"+wechatInfo.getSignature()+"-->unionis:"+session.getUnionid()+"-->openid="+session.getOpenid());
            // 用户信息校验
//            if (!this.wxService.getUserService().checkUserInfo(session.getSessionKey(), wechatInfo.getRawData(), wechatInfo.getSignature())) {
//                return "user check failed";
//            }

            // 解密用户信息
            //WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(session.getSessionKey(), wechatInfo.getEncryptedData(), wechatInfo.getIv());
            UserInfo userInfo  = wechatInfo.getUserInfo();
           // userInfo.setUnionId(session.getUnionid());
            //MiniUser user = userService.getUserByOpenId(session.getOpenid());
            //if(user==null) {
            MiniUser user = userService.addUser(userInfo, systemInfo);
            		MiniIncome miniIncome = new MiniIncome();
            		miniIncome.setAlreadyPresented(0);
            		miniIncome.setCanPresented(0);
            		miniIncome.setDeduct(0);
            		miniIncome.setExpectPresented(0);
            		miniIncome.setMiniUserId(user.getId());
            		miniIncome.setGmtCreate(new Date());
            		miniIncome.setGmtMmodified(new Date());
            		miniIncomeService.add(miniIncome);
           // }
    			LoginResponse loginResponse = new LoginResponse();
    			loginResponse.setUser_info(userInfo);
    			try {
					loginResponse.setHaihu_session(ThreeDES.encryptMode(String.valueOf(user.getId()).getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
    			loginResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
    			loginResponse.setUser_id(String.valueOf(user.getId()));
            return JsonUtils.toJson(loginResponse);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("info")
    public String info(String sessionKey, String signature, String rawData, String encryptedData, String iv) {
        // 用户信息校验
        if (!this.wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = this.wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

}
