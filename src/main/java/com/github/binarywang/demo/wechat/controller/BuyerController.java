package com.github.binarywang.demo.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.bean.MallDefinition;
import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
import com.github.binarywang.demo.wechat.exception.ProcessStatusCode;
import com.github.binarywang.demo.wechat.request.AccountListRequest;
import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.Mall;
import com.github.binarywang.demo.wechat.request.OperationRequest;
import com.github.binarywang.demo.wechat.request.UseAccountRequest;
import com.github.binarywang.demo.wechat.response.AccountListResponse;
import com.github.binarywang.demo.wechat.response.AccountResponse;
import com.github.binarywang.demo.wechat.response.GetCreateResponse;
import com.github.binarywang.demo.wechat.response.IncomeInfo;
import com.github.binarywang.demo.wechat.response.IndexResponse;
import com.github.binarywang.demo.wechat.response.UseAccountResponse;
import com.github.binarywang.demo.wechat.service.MallDefinitionService;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;
import com.github.binarywang.demo.wechat.service.MiniUserService;
import com.github.binarywang.demo.wechat.service.OrderAccountService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
import com.github.binarywang.demo.wechat.utils.ThreeDES;

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
    @Autowired
	private MallDefinitionService mallDefinitionService;
    @Autowired
	private MiniUserService userService;

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
        orderAccount.setAccountSource(String.valueOf(userId));
        try {
			orderAccount.setLoginPwd(ThreeDES.encryptMode(buyerAccount.getPassword().getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        if(!StringUtils.isBlank(buyerAccount.getMail())) {
        		userService.updateMail(userId, buyerAccount.getMail());
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
     * 获取商城列表
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
        List<Mall> malls = new ArrayList<Mall>();
        List<MallDefinition> mallDefinitions = mallDefinitionService.getMallDefinitionList("1,3,5");
        for(MallDefinition m:mallDefinitions) {
        		Mall mall = new Mall();
        		mall.setCountry(m.getCountry());
        		mall.setMall(m.getName());
        		if(StringUtils.isBlank(m.getIcon())) {
        			mall.setMall_icon("img.haihu.com/"+m.getIcon());
        		}
        		mall.setMall_id(String.valueOf(m.getId()));
        		malls.add(mall);
        }
        getCreateResponse.setMall_list(malls);
        return JsonUtils.toJson(getCreateResponse);
    }
    
    /**
     * 账号列表
     */
    @PostMapping("accountList")
    public String accountList(@RequestBody AccountListRequest accountListRequest) {
        if (StringUtils.isBlank(accountListRequest.getHaihu_session())) {
            return "empty session";
        }
        Long mallId = Long.parseLong(accountListRequest.getMall_id());
        Long userId = Long.parseLong(ThreeDES.decryptMode(accountListRequest.getHaihu_session()));
        
        AccountListResponse accountListResponse = new AccountListResponse();
        accountListResponse.setHaihu_session(accountListRequest.getHaihu_session());
        accountListResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        accountListResponse.setUser_id(String.valueOf(userId));
        if(mallId>0) {
	        MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionById(mallId);
	        List<BuyerAccount> accountList = new ArrayList<BuyerAccount>();
	        List<OrderAccount> orderAccounts = orderAccountService.getOrderAccountByAccountSource(String.valueOf(userId), mallDefinition.getName());
	        //获取邮箱
	        MiniUser miniUser =  userService.getUserById(userId);
	        for(OrderAccount orderAccount:orderAccounts) {
	        		BuyerAccount buyerAccount = new BuyerAccount();
	        		buyerAccount.setAccount(orderAccount.getPayAccount());
	        		buyerAccount.setAccount_id(String.valueOf(orderAccount.getAccountId()));
	        		buyerAccount.setMail(miniUser.getMail());
	        		
	        		Mall mall = new Mall();
	        		mall.setCountry(mallDefinition.getCountry());
	        		mall.setMall(mallDefinition.getName());
	        		if(StringUtils.isBlank(mallDefinition.getIcon())) {
	        			mall.setMall_icon("img.haihu.com/"+mallDefinition.getIcon());
	        		}
	        		mall.setMall_id(String.valueOf(mallDefinition.getId()));
	        		
	        		buyerAccount.setMall(mall);
	        		buyerAccount.setStatus(String.valueOf(orderAccount.getStatus()));
	        		accountList.add(buyerAccount);
	        }
	        accountListResponse.setAccount_list(accountList);
        }
        List<Mall> malls = new ArrayList<Mall>();
        List<MallDefinition> mallDefinitions = mallDefinitionService.getMallDefinitionList("1,3,5");
        for(MallDefinition m:mallDefinitions) {
        		Mall mall = new Mall();
        		mall.setCountry(m.getCountry());
        		mall.setMall(m.getName());
        		if(StringUtils.isBlank(m.getIcon())) {
        			mall.setMall_icon("img.haihu.com/"+m.getIcon());
        		}
        		mall.setMall_id(String.valueOf(m.getId()));
        		malls.add(mall);
        }
        accountListResponse.setMall_list(malls);
        return JsonUtils.toJson(accountListResponse);
    }
    
    /**
     * 修改账号
     */
    @PostMapping("modifyAccount")
    public String modifyAccount(@RequestBody BuyerAccount buyerAccount) {
        if (StringUtils.isBlank(buyerAccount.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(buyerAccount.getHaihu_session()));
        
        if(!StringUtils.isBlank(buyerAccount.getMail())) {
        		userService.updateMail(userId, buyerAccount.getMail());
        }
        OrderAccount orderAccount = orderAccountService.getOrderAccountByAccountId(Integer.parseInt(buyerAccount.getAccount_id()));
        if(!StringUtils.isBlank(buyerAccount.getAccount())) {
        		orderAccount.setPayAccount(buyerAccount.getAccount());
        }
        if(!StringUtils.isBlank(buyerAccount.getPassword())) {
	        	try {
	    			orderAccount.setLoginPwd(ThreeDES.encryptMode(buyerAccount.getPassword().getBytes("UTF-8")));
	    		} catch (UnsupportedEncodingException e) {
	    			e.printStackTrace();
	    		}
        }
        orderAccountService.updateOrderAccount(orderAccount);
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setHaihu_session(buyerAccount.getHaihu_session());
        accountResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        accountResponse.setUser_id(String.valueOf(userId));
        accountResponse.setAccount_id(buyerAccount.getAccount_id());
        return JsonUtils.toJson(accountResponse);
    }
    
    /**
     * 账号（暂停/恢复）使用
     */
    @PostMapping("useAccount")
    public String useAccount(@RequestBody UseAccountRequest useAccountRequest) {
        if (StringUtils.isBlank(useAccountRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(useAccountRequest.getHaihu_session()));
        
        OrderAccount orderAccount = orderAccountService.getOrderAccountByAccountId(Integer.parseInt(useAccountRequest.getAccount_id()));
        if("0".equals(useAccountRequest.getStatus())) {
        		orderAccount.setStatus(1);
        }else if("1".equals(useAccountRequest.getStatus())) {
        		orderAccount.setStatus(0);
        }
        orderAccountService.updateOrderAccount(orderAccount);
        UseAccountResponse useAccountResponse = new UseAccountResponse();
        useAccountResponse.setHaihu_session(useAccountRequest.getHaihu_session());
        useAccountResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        useAccountResponse.setUser_id(String.valueOf(userId));
        useAccountResponse.setDesc("修改成功");
        
        BuyerAccount buyerAccount = new BuyerAccount();
		buyerAccount.setAccount(orderAccount.getPayAccount());
		buyerAccount.setAccount_id(String.valueOf(orderAccount.getAccountId()));
		Mall mall = new Mall();
		mall.setMall(orderAccount.getAccountType());
		buyerAccount.setMall(mall);
		buyerAccount.setStatus(String.valueOf(orderAccount.getStatus()));
        useAccountResponse.setAccount(buyerAccount);
        return JsonUtils.toJson(useAccountResponse);
    }



}
