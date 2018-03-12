package com.github.binarywang.demo.wechat.scheduled;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;
import com.github.binarywang.demo.wechat.service.MiniIncomeDetailService;

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


    @Scheduled(cron = "0 0/1 * * * ?") // 每1分钟执行一次
    public void pushOrder() {
    		List<MiniOrder> miniOrders = miniOrderMapper.getMiniOrderList(10);
    		for(MiniOrder miniOrder:miniOrders) {
    			//推送消息
    			miniOrderMapper.updateStatus(miniOrder.getId(), 0);
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
    				miniIncomeDetail.setOrder_no(dateStr);
    				miniIncomeDetail.setTitle("申请提现");
    				miniIncomeDetail.setType(3);
    				miniIncomeDetailService.add(miniIncomeDetail);
    			}
    		}
        
    }
}
