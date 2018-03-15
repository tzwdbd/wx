package com.github.binarywang.demo.wechat.scheduled;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.binarywang.demo.wechat.bean.AlipayTradeMoney;
import com.github.binarywang.demo.wechat.bean.MiniIncomeDetail;
import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.AlipayTradeMoneyMapper;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeDetailMapper;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeMapper;
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.service.MiniIncomeDetailService;
import com.google.common.collect.Lists;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage.KfText;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;

@Configuration
@EnableScheduling
public class PushOrder {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MiniOrderMapper miniOrderMapper;
	@Autowired
	private AlipayTradeMoneyMapper alipayTradeMoneyMapper;
	@Autowired
	private MiniIncomeDetailService miniIncomeDetailService;
	@Autowired
	private MiniIncomeDetailMapper miniIncomeDetailMapper;
	@Autowired
	private MiniIncomeMapper miniIncomeMapper;
	@Autowired
	protected WxMaService wxService;
	@Autowired
	private MiniUserMapper miniUserMapper;


    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
    public void pushOrder() {
    		List<MiniOrder> miniOrders = miniOrderMapper.getMiniOrderList(10);
    		List<String> orders = new ArrayList<String>();
    		for(MiniOrder miniOrder:miniOrders) {
    			//推送消息
    			miniOrderMapper.updateStatus(miniOrder.getId(), 0);
    			if(!orders.contains(miniOrder.getOrderNo())) {
	    			MiniUser miniUser = miniUserMapper.getUserById(miniOrder.getMiniUserId());
				try {
					wxService.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
                    .templateId("N916r-hHmRpJNpnCe2oxO_rpA5OhSj5SE2P62ZOpMx4")
                    .formId("自己替换可用的formid")
                    .data(Lists.newArrayList(
                            new WxMaTemplateMessage.Data("keyword1", "收到商城"+miniOrder.getSiteName()+"的订单", "#173177")))
                    .toUser(miniUser.getOpenId())
                    .page("index")
                    .build());
					//(WxMaKefuMessage.newTextBuilder().content("收到商城"+miniOrder.getSiteName()+"的订单<a href=\"http://www.qq.com\" data-miniprogram-appid=\"wx84cc48c8ddcf5e08\" data-miniprogram-path=\"pages/index/index\">点击跳小程序</a>").toUser(miniUser.getOpenId()).build());
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
				orders.add(miniOrder.getOrderNo());
    			}
    		}
        
    }
    
    @Scheduled(cron = "1 0/1 * * * ?") // 每1分钟执行一次
    public void updateOrder() {
    		List<MiniOrder> miniOrders = miniOrderMapper.getMiniOrderList(0);
    		for(MiniOrder miniOrder:miniOrders) {
    			//大于30分钟
    			if((new Date().getTime()-miniOrder.getGmtCreate().getTime())/1000/60>30){
    				miniOrderMapper.updateStatus(miniOrder.getId(), 1);
    			}
    		}
        
    }
    
    @Scheduled(cron = "2 * 0/1 * * ?") // 每1小时执行一次
    public void tradeMoney() {
    		List<AlipayTradeMoney> alipayTradeMoneys = alipayTradeMoneyMapper.getAlipayTradeMoneyList();
    		for(AlipayTradeMoney alipayTradeMoney:alipayTradeMoneys) {
    			int num = alipayTradeMoneyMapper.update(alipayTradeMoney.getId());
    			if(num>0) {
    				MiniIncomeDetail miniIncomeDetail = new MiniIncomeDetail();
    				BigDecimal b1 = new BigDecimal(alipayTradeMoney.getCouponValue());
    			    BigDecimal b2 = new BigDecimal(100);
    				miniIncomeDetail.setIncome(String.valueOf(b1.divide(b2, 2, 4)));
    				miniIncomeDetail.setMiniUserId(alipayTradeMoney.getUserId());
    				DateFormat ymdhmsFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
    				String dateStr = ymdhmsFormat.format(new Date());
    				miniIncomeDetail.setOrderNo(dateStr);
    				miniIncomeDetail.setTitle("申请提现");
    				miniIncomeDetail.setType(3);
    				miniIncomeDetailService.add(miniIncomeDetail);
    			}
    		}
        
    }
    
    @Scheduled(cron = "3 * 0/1 * * ?") // 每1分钟执行一次
    public void addIncome() {
    		List<MiniOrder> miniOrders = miniOrderMapper.getMiniOrderList(13);
    		List<String> orders = new ArrayList<String>();
    		for(MiniOrder miniOrder:miniOrders) {
    			miniOrderMapper.updateStatus(miniOrder.getId(), 14);
    			if(!orders.contains(miniOrder.getOrderNo())) {
    				MiniIncomeDetail miniIncomeDetail = miniIncomeDetailMapper.getMiniIncomeDetailByOrderNo(miniOrder.getMiniUserId(), miniOrder.getOrderNo());
    				miniIncomeDetailMapper.updateMiniIncomeDetailByOrderNo(1, miniOrder.getOrderNo());
    				orders.add(miniOrder.getOrderNo());
    				if(miniIncomeDetail!=null) {
    					miniIncomeMapper.updateReduceExpectPresented(miniOrder.getMiniUserId(), Integer.parseInt(miniIncomeDetail.getIncome()));
    				}
    			}
    			
    		}
        
    }
}
