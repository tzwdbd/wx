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
import com.github.binarywang.demo.wechat.mapper.AlipayTradeMoneyMapper;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeDetailMapper;
import com.github.binarywang.demo.wechat.mapper.MiniIncomeMapper;
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;
import com.github.binarywang.demo.wechat.service.MiniIncomeDetailService;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
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


    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
    public void pushOrder() {
    		List<MiniOrder> miniOrders = miniOrderMapper.getMiniOrderList(10);
    		for(MiniOrder miniOrder:miniOrders) {
    			//推送消息
    			miniOrderMapper.updateStatus(miniOrder.getId(), 0);
    			
    			 WxMaKefuMessage message = new WxMaKefuMessage();
    			 message.setMsgType(WxConsts.KefuMsgType.TEXT);
    			 message.setToUser("oAra84qw3VcLYzS2tZ2NfRgkAdiw");
    			 KfText test = new KfText("欢迎欢迎，热烈欢迎\n换行测试\n超链接:<a href=\"http://www.baidu.com\">Hello World</a>");
    			 message.setText(test);

    			 try {
					wxService.getMsgService().sendKefuMsg(message);
				} catch (WxErrorException e) {
					e.printStackTrace();
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
    				miniIncomeDetail.setMiniUserId(alipayTradeMoney.getUser_id());
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
