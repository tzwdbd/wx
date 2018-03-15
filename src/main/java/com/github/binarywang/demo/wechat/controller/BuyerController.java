package com.github.binarywang.demo.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.bean.AlipayTradeMoney;
import com.github.binarywang.demo.wechat.bean.MallDefinition;
import com.github.binarywang.demo.wechat.bean.MiniIncome;
import com.github.binarywang.demo.wechat.bean.MiniIncomeDetail;
import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.bean.OrderAccount;
import com.github.binarywang.demo.wechat.bean.OrderDetail;
import com.github.binarywang.demo.wechat.exception.ProcessStatusCode;
import com.github.binarywang.demo.wechat.mapper.AlipayTradeMoneyMapper;
import com.github.binarywang.demo.wechat.mapper.ProductMapper;
import com.github.binarywang.demo.wechat.request.AccountListRequest;
import com.github.binarywang.demo.wechat.request.ApplyCashRequest;
import com.github.binarywang.demo.wechat.request.BuyerAccount;
import com.github.binarywang.demo.wechat.request.ConfirmCodeRequest;
import com.github.binarywang.demo.wechat.request.IncomeDetailRequest;
import com.github.binarywang.demo.wechat.request.Mall;
import com.github.binarywang.demo.wechat.request.OperationRequest;
import com.github.binarywang.demo.wechat.request.OrderDetailRequest;
import com.github.binarywang.demo.wechat.request.OrderListRequest;
import com.github.binarywang.demo.wechat.request.UseAccountRequest;
import com.github.binarywang.demo.wechat.response.AccountListResponse;
import com.github.binarywang.demo.wechat.response.AccountResponse;
import com.github.binarywang.demo.wechat.response.ApplyCashResponse;
import com.github.binarywang.demo.wechat.response.BuyerExpressNode;
import com.github.binarywang.demo.wechat.response.BuyerGoods;
import com.github.binarywang.demo.wechat.response.BuyerPackage;
import com.github.binarywang.demo.wechat.response.ConfirmAuthResponse;
import com.github.binarywang.demo.wechat.response.ConfirmCodeResponse;
import com.github.binarywang.demo.wechat.response.GetCreateResponse;
import com.github.binarywang.demo.wechat.response.IncomeDetailInfo;
import com.github.binarywang.demo.wechat.response.IncomeDetailResponse;
import com.github.binarywang.demo.wechat.response.IncomeInfo;
import com.github.binarywang.demo.wechat.response.IndexResponse;
import com.github.binarywang.demo.wechat.response.OrderDetailResponse;
import com.github.binarywang.demo.wechat.response.OrderInfo;
import com.github.binarywang.demo.wechat.response.OrderListResponse;
import com.github.binarywang.demo.wechat.response.UseAccountResponse;
import com.github.binarywang.demo.wechat.service.MallDefinitionService;
import com.github.binarywang.demo.wechat.service.MiniIncomeDetailService;
import com.github.binarywang.demo.wechat.service.MiniIncomeService;
import com.github.binarywang.demo.wechat.service.MiniOrderService;
import com.github.binarywang.demo.wechat.service.MiniUserService;
import com.github.binarywang.demo.wechat.service.OrderAccountService;
import com.github.binarywang.demo.wechat.service.OrderDetailService;
import com.github.binarywang.demo.wechat.utils.JsonUtils;
import com.github.binarywang.demo.wechat.utils.ThreeDES;
import com.google.gson.Gson;

/**
 * 账号处理
 *
 * @author liuxf
 */
@RestController
@RequestMapping("/mp/buyer")
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
    @Autowired
	private MiniOrderService miniOrderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private MiniIncomeDetailService miniIncomeDetailService;
    @Autowired
    private AlipayTradeMoneyMapper alipayTradeMoneyMapper;

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
        	List<MiniOrder> miniOrders = miniOrderService.getMiniOrderList(1, null, userId,null,10000);
        	indexResponse.setUnauth_num(String.valueOf(miniOrders.size()));
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
        orderAccount.setStatus(2);
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
        
        List<Mall> malls = getMallList();
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
      
        List<BuyerAccount> accountList = new ArrayList<BuyerAccount>();
        List<OrderAccount> orderAccounts = orderAccountService.getAOrderAccountByAccountSource(String.valueOf(userId));
        //获取邮箱
        MiniUser miniUser =  userService.getUserById(userId);
        for(OrderAccount orderAccount:orderAccounts) {
        		BuyerAccount buyerAccount = new BuyerAccount();
        		buyerAccount.setAccount(orderAccount.getPayAccount());
        		buyerAccount.setAccount_id(String.valueOf(orderAccount.getAccountId()));
        		buyerAccount.setMail(miniUser.getMail());
        		MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionByName(orderAccount.getAccountType());
        		Mall mall = getMall(mallDefinition);
        		buyerAccount.setMall(mall);
        		buyerAccount.setStatus(String.valueOf(orderAccount.getStatus()));
        		accountList.add(buyerAccount);
        }
        accountListResponse.setAccount_list(accountList);
        	if(mallId==0) {
	        List<Mall> malls = getMallList();
	        accountListResponse.setMall_list(malls);
        }
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
        		orderAccount.setStatus(0);
        }else if("1".equals(useAccountRequest.getStatus())) {
        		orderAccount.setStatus(1);
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
		MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionByName(orderAccount.getAccountType());
		Mall mall = getMall(mallDefinition);
		buyerAccount.setMall(mall);
		buyerAccount.setStatus(String.valueOf(orderAccount.getStatus()));
        useAccountResponse.setAccount(buyerAccount);
        return JsonUtils.toJson(useAccountResponse);
    }
    
    /**
     * 订单列表
     */
    @PostMapping("orderManger")
    public String orderManger(@RequestBody OrderListRequest orderListRequest) {
        if (StringUtils.isBlank(orderListRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(orderListRequest.getHaihu_session()));
        Long mallId = Long.parseLong(orderListRequest.getMall_id());
        Date time = null;
        if(!StringUtils.isBlank(orderListRequest.getCreate_time())) {
        		time = new Date(Long.parseLong(orderListRequest.getCreate_time()));
        }
        int pageSize = 20;
        if(!StringUtils.isBlank(orderListRequest.getPage_size())) {
        		pageSize = Integer.parseInt(orderListRequest.getPage_size());
        }
        
        String siteName = null;
        Integer type = Integer.parseInt(orderListRequest.getType());
        if(mallId>0) {
        		MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionById(mallId);
        		siteName = mallDefinition.getName();
        }
        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.setHaihu_session(orderListRequest.getHaihu_session());
        orderListResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        orderListResponse.setUser_id(String.valueOf(userId));
        List<MiniOrder> miniOrders = miniOrderService.getMiniOrderList(type, siteName, userId,time,pageSize);
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        List<String> orders = new ArrayList<String>();
        for(MiniOrder m:miniOrders) {
        		if(!orders.contains(m.getOrderNo())) {
        			List<OrderDetail> orderDetails = orderDetailService.getOrderDetailList(m.getOrderNo());
        			OrderInfo orderInfo = getOrderInfo(orderDetails,userId);
	        		orderList.add(orderInfo);
	        		orders.add(m.getOrderNo());
        		}
        }
        orderListResponse.setOrder_list(orderList);
        List<Mall> mallList = getMallList();
        orderListResponse.setMall_list(mallList);
        return JsonUtils.toJson(orderListResponse);
    }
    
    /**
     * 订单详情
     */
    @PostMapping("orderDetails")
    public String orderDetails(@RequestBody OrderDetailRequest orderDetailRequest) {
        if (StringUtils.isBlank(orderDetailRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(orderDetailRequest.getHaihu_session()));
        String orderNo = orderDetailRequest.getOrder_no();
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailList(orderNo);
        OrderInfo orderInfo = getOrderInfo(orderDetails,userId);
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setHaihu_session(orderDetailRequest.getHaihu_session());
        orderDetailResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        orderDetailResponse.setUser_id(String.valueOf(userId));
        orderDetailResponse.setOrder(orderInfo);
        
        if(orderDetails.get(0).getAccountId()!=null && orderDetails.get(0).getAccountId()>0) {
	        OrderAccount orderAccount = orderAccountService.getOrderAccountByAccountId(orderDetails.get(0).getAccountId());
	        BuyerAccount buyerAccount = new BuyerAccount();
			buyerAccount.setAccount(orderAccount.getPayAccount());
			buyerAccount.setAccount_id(String.valueOf(orderAccount.getAccountId()));
			MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionByName(orderAccount.getAccountType());
			Mall mall = getMall(mallDefinition);
			buyerAccount.setMall(mall);
			buyerAccount.setStatus(String.valueOf(orderAccount.getStatus()));
	        orderDetailResponse.setAccount(buyerAccount);
        }
        String status = orderDetailService.getStatus(orderDetails, userId);
        List<BuyerExpressNode> expressNodeList = new ArrayList<BuyerExpressNode>();
    		BuyerExpressNode buyerExpressNode1 = new BuyerExpressNode();
    		buyerExpressNode1.setCode("3");
    		buyerExpressNode1.setDisplay("商城发货");
    		buyerExpressNode1.setStatus("1");
    		if(Integer.parseInt(status)>=4) {
    			buyerExpressNode1.setStatus("0");
    		}
    		
    		expressNodeList.add(buyerExpressNode1);
    		BuyerExpressNode buyerExpressNode2 = new BuyerExpressNode();
    		buyerExpressNode2.setCode("4");
    		buyerExpressNode2.setDisplay("确认签收");
    		buyerExpressNode2.setStatus("1");
    		if(Integer.parseInt(status)>=6) {
    			buyerExpressNode2.setStatus("0");
    		}
    		
    		expressNodeList.add(buyerExpressNode2);
    		BuyerExpressNode buyerExpressNode3 = new BuyerExpressNode();
    		buyerExpressNode3.setCode("5");
    		buyerExpressNode3.setDisplay("中转仓出库");
    		buyerExpressNode3.setStatus("1");
    		if(Integer.parseInt(status)>=7) {
    			buyerExpressNode3.setStatus("0");
    		}
    		expressNodeList.add(buyerExpressNode3);
    		BuyerExpressNode buyerExpressNode4 = new BuyerExpressNode();
    		buyerExpressNode4.setCode("6");
    		buyerExpressNode4.setDisplay("用户签收");
    		buyerExpressNode4.setStatus("1");
    		if(Integer.parseInt(status)==14) {
    			buyerExpressNode3.setStatus("0");
    		}
    		expressNodeList.add(buyerExpressNode4);
        orderDetailResponse.setExpress_node_list(expressNodeList);
        return JsonUtils.toJson(orderDetailResponse);
    }
    
    /**
     * 确认授权订单
     */
    @PostMapping("confirmAuth")
    public String confirmAuth(@RequestBody OrderDetailRequest orderDetailRequest) {
        if (StringUtils.isBlank(orderDetailRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(orderDetailRequest.getHaihu_session()));
        String orderNo = orderDetailRequest.getOrder_no();
        ConfirmAuthResponse confirmAuthResponse = new ConfirmAuthResponse();
        String status =ProcessStatusCode.PROCESS_FAIL.getCode();
        int num = miniOrderService.updateMiniOrderByOrder(orderNo, 0, 3);
        if(num>0) {
        		status=ProcessStatusCode.PROCESS_SUCCESS.getCode();
        }
        confirmAuthResponse.setHaihu_session(orderDetailRequest.getHaihu_session());
        confirmAuthResponse.setStatus(status);
        confirmAuthResponse.setUser_id(String.valueOf(userId));
        return JsonUtils.toJson(confirmAuthResponse);
    }
    
    /**
     * 确认收货
     */
    @PostMapping("confirmCode")
    public String confirmCode(@RequestBody ConfirmCodeRequest ConfirmCodeRequest) {
        if (StringUtils.isBlank(ConfirmCodeRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(ConfirmCodeRequest.getHaihu_session()));
        String expressNo = ConfirmCodeRequest.getExpress_no();
        ConfirmCodeResponse confirmCodeResponse = new ConfirmCodeResponse();
        String success =ProcessStatusCode.PROCESS_FAIL.getCode();
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByExpressList(expressNo);
        if(orderDetails!=null && orderDetails.size()>0) {
        		for(OrderDetail orderDetail:orderDetails) {
        			MiniOrder miniOrder = miniOrderService.getOrderDetailByOrderNoAndSkuId(orderDetail.getOrderNo(), orderDetail.getProductEntityId(),userId);
        			if(miniOrder!=null) {
        				int num = miniOrderService.updateMiniOrderById(miniOrder.getId(), 3, 6);
        				if(num>0) {
        					success=ProcessStatusCode.PROCESS_SUCCESS.getCode();
        				}
        			}
        		}
        }
      //算收益
        if(success.equals(ProcessStatusCode.PROCESS_SUCCESS.getCode())) {
			miniIncomeService.updateExpectPresented(userId, 5);
			MiniIncomeDetail miniIncomeDetail = new MiniIncomeDetail();
			miniIncomeDetail.setIncome("5");
			miniIncomeDetail.setMiniUserId(userId);
			miniIncomeDetail.setOrderNo(orderDetails.get(0).getOrderNo());
			miniIncomeDetail.setTitle(orderDetails.get(0).getSiteName());
			miniIncomeDetail.setType(1);
			miniIncomeDetailService.add(miniIncomeDetail);
        }
        confirmCodeResponse.setHaihu_session(ConfirmCodeRequest.getHaihu_session());
        confirmCodeResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        confirmCodeResponse.setUser_id(String.valueOf(userId));
        confirmCodeResponse.setSuccess(success);
        return JsonUtils.toJson(confirmCodeResponse);
    }
    
    /**
     * 买手收益明细
     */
    @PostMapping("incomeDetails")
    public String incomeDetails(@RequestBody IncomeDetailRequest incomeDetailRequest) {
        if (StringUtils.isBlank(incomeDetailRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(incomeDetailRequest.getHaihu_session()));
        Integer pageNo = Integer.parseInt(incomeDetailRequest.getPage_no());
        Integer pageSize = Integer.parseInt(incomeDetailRequest.getPage_size());
        IncomeDetailResponse incomeDetailResponse = new IncomeDetailResponse();
        incomeDetailResponse.setHaihu_session(incomeDetailRequest.getHaihu_session());
        incomeDetailResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
        incomeDetailResponse.setUser_id(String.valueOf(userId));
        incomeDetailResponse.setPage_no(incomeDetailRequest.getPage_no());
        incomeDetailResponse.setPage_size(incomeDetailRequest.getPage_size());
        List<MiniIncomeDetail>  miniIncomeDetails = miniIncomeDetailService.getMiniIncomeDetailByUserId(userId, pageNo, pageSize);
        List<IncomeDetailInfo> incomeDetailInfos = new ArrayList<IncomeDetailInfo>();
        for(MiniIncomeDetail miniIncomeDetail:miniIncomeDetails) {
        		IncomeDetailInfo incomeDetailInfo = new IncomeDetailInfo();
        		incomeDetailInfo.setDate(String.valueOf(miniIncomeDetail.getGmtModified().getTime()));
        		incomeDetailInfo.setIncome(miniIncomeDetail.getIncome());
        		incomeDetailInfo.setOrder_no(miniIncomeDetail.getOrderNo());
        		incomeDetailInfo.setTitle(miniIncomeDetail.getTitle());
        		incomeDetailInfo.setType(String.valueOf(miniIncomeDetail.getType()));
        		incomeDetailInfos.add(incomeDetailInfo);
        }
        incomeDetailResponse.setIncome_detail_list(incomeDetailInfos);
        return JsonUtils.toJson(incomeDetailResponse);
    }
    
    /**
     * 提现申请
     */
    @PostMapping("applyCash")
    public String applyCash(@RequestBody ApplyCashRequest applyCashRequest) {
        if (StringUtils.isBlank(applyCashRequest.getHaihu_session())) {
            return "empty session";
        }
        Long userId = Long.parseLong(ThreeDES.decryptMode(applyCashRequest.getHaihu_session()));
        String alipay = applyCashRequest.getAlipay();
        String amount = applyCashRequest.getCash_amount();
        String name = applyCashRequest.getReally_name();
        String type = applyCashRequest.getType();
        MiniIncome miniIncome = miniIncomeService.getMiniIncomeByUserId(userId);
        BigDecimal fen =  new BigDecimal(amount).multiply(new BigDecimal("100"));
        int coupon = fen.intValue();
        ApplyCashResponse applyCashResponse = new ApplyCashResponse();
        if(miniIncome.getCanPresented()>=coupon) {
        		int num = miniIncomeService.updateCanPresented(userId, coupon);
        		if(num>0) {
	        		AlipayTradeMoney alipayTradeMoney = new AlipayTradeMoney();
	            alipayTradeMoney.setAlipayAccount(alipay);
	            alipayTradeMoney.setRealName(name);
	            alipayTradeMoney.setGmtCreate(new Date());
	            alipayTradeMoney.setGmtModified(new Date());
	            alipayTradeMoney.setType(2);
	            alipayTradeMoney.setUser_id(userId);;
	            alipayTradeMoneyMapper.add(alipayTradeMoney);
	           
	            applyCashResponse.setHaihu_session(applyCashRequest.getHaihu_session());
	            applyCashResponse.setStatus(ProcessStatusCode.PROCESS_SUCCESS.getCode());
	            applyCashResponse.setUser_id(String.valueOf(userId));
	            applyCashResponse.setDesc("提现成功");
        		}
        }else {
        		applyCashResponse.setDesc("提现金额超过可提现数");
        }
        
        return JsonUtils.toJson(applyCashResponse);
    }
    
    public static String getSku(String value) {
        Gson gson = new Gson();
        String str = "";
        ArrayList list = gson.fromJson(value, ArrayList.class);
        for (Object s : list) {
            List al = (List) s;
            int i = 0;
            for (Object ss : al) {
            		if(i==0) {
            			str = str+ss;
            		}else {
            			str = str+":"+ss+" ";
            		}
            		i++;
            }
        }
        return str;
    }
    
    OrderInfo getOrderInfo(List<OrderDetail> orderDetails,Long userId){
    		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreate_time(String.valueOf(orderDetails.get(0).getGmtCreate().getTime()));
		if(System.currentTimeMillis()-orderDetails.get(0).getGmtCreate().getTime()>30*60*1000) {
			orderInfo.setEnd_time("0");
		}else {
			orderInfo.setEnd_time(String.valueOf(System.currentTimeMillis()-orderDetails.get(0).getGmtCreate().getTime()));
		}
		
		List<BuyerGoods> goodsList = new ArrayList<BuyerGoods>();
		List<BuyerPackage> packageList = new ArrayList<BuyerPackage>();
		List<BuyerGoods> packageGoodsList = new ArrayList<BuyerGoods>();
		float myPrice = 0f ;
		float rmbPrice = 0f ;
		for(OrderDetail orderDetail:orderDetails) {
			BuyerGoods buyerGoods = new BuyerGoods();
			String img = productMapper.getProductImg(orderDetail.getProductId());
			buyerGoods.setImg("http://img.haihu.com/"+img);
			String name = productMapper.getProductName(orderDetail.getProductId());
			buyerGoods.setMall_price("$"+orderDetail.getMyPrice());
			String status = orderDetailService.getStatus(orderDetail, userId);
			buyerGoods.setStatus(status);
			buyerGoods.setNumber(String.valueOf(orderDetail.getNum()));
			buyerGoods.setSeq(String.valueOf(orderDetail.getProductEntityId()));
			if(!StringUtils.isBlank(orderDetail.getProductSku())) {
				buyerGoods.setSeq_desc(getSku(orderDetail.getProductSku()));
			}
			buyerGoods.setTitle(name);
			if(orderDetail.getStatus()==100) {
				BuyerPackage buyerPackage = new BuyerPackage();
				packageGoodsList.add(buyerGoods);
				buyerPackage.setGoods_list(packageGoodsList);
				buyerPackage.setStatus(status);
				buyerPackage.setExpress_no(orderDetail.getExpressNo());
				packageList.add(buyerPackage);
			}else {
				goodsList.add(buyerGoods);
			}
			
			myPrice += Float.parseFloat(orderDetail.getMyPrice()) * orderDetail.getNum();
			rmbPrice += Float.parseFloat(orderDetail.getRmbPrice()) * orderDetail.getNum();
		}
		orderInfo.setGoods_list(goodsList);
		orderInfo.setPackage_list(packageList);
		orderInfo.setMall_no(orderDetails.get(0).getMallOrderNo());
		orderInfo.setIncome("¥5");
		MallDefinition mallDefinition = mallDefinitionService.getMallDefinitionByName(orderDetails.get(0).getSiteName());
		Mall mall = getMall(mallDefinition);
		orderInfo.setMall(mall);
		orderInfo.setOrder_no(orderDetails.get(0).getOrderNo());
		if(orderDetails.get(0).getOrderTime()!=null) {
			orderInfo.setOrder_time(String.valueOf(orderDetails.get(0).getOrderTime().getTime()));
		}
		
		orderInfo.setRmb_price("¥"+String.valueOf(rmbPrice));
		String status = orderDetailService.getStatus(orderDetails, userId);
		orderInfo.setStatus(String.valueOf(status));
		orderInfo.setTotal_price("$"+String.valueOf(myPrice));
		if("2".equals(orderInfo.getStatus())) {
			orderInfo.setRelease(orderDetails.get(0).getRemarks());
		}
		return orderInfo;
    }

    private List<Mall> getMallList() {
    		List<Mall> malls = new ArrayList<Mall>();
    		/*TODO*/
        List<MallDefinition> mallDefinitions = mallDefinitionService.getMallDefinitionList("1,3,5");
        for(MallDefinition m:mallDefinitions) {
        		Mall mall = 	getMall(m);
        		malls.add(mall);
        }
        return malls;
	}
    
    private Mall getMall(MallDefinition mallDefinition) {
    		Mall mall = new Mall();
		mall.setCountry(mallDefinition.getCountry());
		mall.setMall(mallDefinition.getName());
		if(StringUtils.isBlank(mallDefinition.getIcon())) {
			mall.setMall_icon("http://img.haihu.com/"+mallDefinition.getIcon());
		}
		mall.setMall_id(String.valueOf(mallDefinition.getId()));
		return mall;
	}

}
