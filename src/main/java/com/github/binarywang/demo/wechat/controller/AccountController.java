package com.github.binarywang.demo.wechat.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

import com.github.binarywang.demo.wechat.bean.User;
import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.UserInfo;
import com.github.binarywang.demo.wechat.service.UserService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
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

import javax.servlet.http.HttpServletRequest;

/**
 * 账号处理
 *
 * @author liuxf
 */
@RestController
@RequestMapping("/buyer")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxMaService wxService;
    @Autowired
	private UserService userService;

    /**
     * 添加账号
     */
    @PostMapping("addAccount")
    public String login(String code,@RequestBody BuyerAccount buyerAccount) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }
        
        User users = userService.getUserById(1835l);
        //System.out.println(userInfo.getNickName());
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

    /**
     * <pre>
     * 获取账号信息接口
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
