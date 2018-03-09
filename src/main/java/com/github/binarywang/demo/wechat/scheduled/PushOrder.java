package com.github.binarywang.demo.wechat.scheduled;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;

@Configuration
@EnableScheduling
public class PushOrder {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MiniOrderMapper miniOrderMapper;


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
}