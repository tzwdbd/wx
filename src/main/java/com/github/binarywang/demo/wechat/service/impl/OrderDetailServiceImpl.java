package com.github.binarywang.demo.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.OrderDetail;
import com.github.binarywang.demo.wechat.mapper.OrderDetailMapper;
import com.github.binarywang.demo.wechat.mapper.UserTradeExpressMapper;
import com.github.binarywang.demo.wechat.service.MiniOrderService;
import com.github.binarywang.demo.wechat.service.OrderDetailService;
import com.google.common.collect.Lists;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
/**
 * @author liuxf
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private MiniOrderService miniOrderService;
	@Autowired
	private UserTradeExpressMapper userTradeExpressMapper;
	@Autowired
	protected WxMaService wxService;

	@Override
	public OrderDetail getOrderDetailByOrderNoAndSkuId(String orderNo, Long productEntityId) {
		return orderDetailMapper.getOrderDetailByOrderNoAndSkuId(orderNo, productEntityId);
	}

	@Override
	public List<OrderDetail> getOrderDetailList(String orderNo) {
		return orderDetailMapper.getOrderDetailList(orderNo);
	}

	@Override
	public List<OrderDetail> getOrderDetailByExpressList(String expressNo) {
		return orderDetailMapper.getOrderDetailByExpressList(expressNo);
	}

	@Override
	public String getStatus(OrderDetail orderDetail,Long userId) {
		MiniOrder miniOrder = miniOrderService.getOrderDetailByOrderNoAndSkuId(orderDetail.getOrderNo(), orderDetail.getProductEntityId(),userId);
		int status = 0;
		if(miniOrder.getStatus()<3) {
			status = miniOrder.getStatus();
		}else {
			if(orderDetail.getStatus() ==0 || orderDetail.getStatus()==2002) {
				status = 3;
			}
			if(orderDetail.getStatus()>0 && orderDetail.getStatus()<=99) {
				status=4;
			}
			if(orderDetail.getStatus()==1000) {
				status = 4;
			}
			if(orderDetail.getStatus() ==13 || orderDetail.getStatus() ==14) {
				status = 2;
			}
			if(orderDetail.getStatus()==100) {
				status = 5;
			}
			if(miniOrder.getStatus()==6) {
				status = 6;
				
				//物流
				Integer expressStatus = userTradeExpressMapper.getUserTradeExpressByOrderNo(orderDetail.getOrderNo());
				if(expressStatus!=null && expressStatus>3) {
					status = 7;
					if(expressStatus==14) {
						status = 14;
					}
				}
			}
			if(miniOrder.getStatus()<status) {
				if(status==14) {
					miniOrderService.updateStatus(miniOrder.getId(), 13);
				}else {
					miniOrderService.updateStatus(miniOrder.getId(), status);
				}
			}else {
				status = miniOrder.getStatus();
			}
		}
		return String.valueOf(status);
	}

	@Override
	public String getStatus(List<OrderDetail> orderDetails, Long userId) {
		int  status= 14;
		for(OrderDetail orderDetail:orderDetails) {
			MiniOrder miniOrder = miniOrderService.getOrderDetailByOrderNoAndSkuId(orderDetail.getOrderNo(), orderDetail.getProductEntityId(),userId);
			int tostatus = miniOrder.getStatus();
//			if(miniOrder.getStatus()<3) {
//				tostatus = miniOrder.getStatus();
//			}else {
//				if(orderDetail.getStatus() ==0 || orderDetail.getStatus()==2002) {
//					tostatus = 3;
//				}
//				if(orderDetail.getStatus()>0 && orderDetail.getStatus()<=99) {
//					tostatus=4;
//				}
//				if(orderDetail.getStatus()==1000) {
//					tostatus = 4;
//				}
//				if(orderDetail.getStatus() ==13 || orderDetail.getStatus() ==14) {
//					tostatus = 2;
//				}
//				if(orderDetail.getStatus()==100) {
//					tostatus = 5;
//				}
//				if(miniOrder.getStatus()==6) {
//					tostatus = 6;
//					//物流
//					Integer expressStatus = userTradeExpressMapper.getUserTradeExpressByOrderNo(orderDetail.getOrderNo());
//					if(expressStatus!=null && expressStatus>3) {
//						tostatus = 7;
//						if(expressStatus==14) {
//							tostatus = 14;
//						}
//					}
//				}
//			}
			if(status>tostatus) {
				status = tostatus;
			}
		}
		return String.valueOf(status);
	}

	@Override
	public void sendMsg(String openId, String formId,String name) {
		try {
			wxService.getMsgService().sendTemplateMsg(WxMaTemplateMessage.builder()
            .templateId("N916r-hHmRpJNpnCe2oxO_rpA5OhSj5SE2P62ZOpMx4")
            .formId(formId)
            .data(Lists.newArrayList(
                    new WxMaTemplateMessage.Data("keyword1", "收到商城"+name+"的订单", "#173177")))
            .toUser(openId)
            .page("pages/buyer/index")
            .build());
			//(WxMaKefuMessage.newTextBuilder().content("收到商城"+miniOrder.getSiteName()+"的订单<a href=\"http://www.qq.com\" data-miniprogram-appid=\"wx84cc48c8ddcf5e08\" data-miniprogram-path=\"pages/index/index\">点击跳小程序</a>").toUser(miniUser.getOpenId()).build());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		
	}



}
