package com.github.binarywang.demo.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.binarywang.demo.wechat.bean.MiniOrder;
import com.github.binarywang.demo.wechat.bean.MiniUser;
import com.github.binarywang.demo.wechat.mapper.MiniOrderMapper;
import com.github.binarywang.demo.wechat.mapper.MiniUserMapper;
import com.github.binarywang.demo.wechat.request.SystemInfo;
import com.github.binarywang.demo.wechat.service.MiniOrderService;
import com.github.binarywang.demo.wechat.service.MiniUserService;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
/**
 * @author liuxf
 */
@Service
public class MiniOrderServiceImpl implements MiniOrderService {
	
	@Autowired
	private MiniOrderMapper miniOrderMapper;

	@Override
	public List<MiniOrder> getMiniOrderList(Integer type,String siteName,Long userId,Date time,Integer pageSize) {
		//全部
		if(type==0) {
			if(siteName ==null) {
				if(time!=null) {
					return miniOrderMapper.getAllMiniOrderByTimeList(userId, pageSize, time);
				}else {
					return miniOrderMapper.getAllMiniOrderList(userId,pageSize);
				}
			}else {
				if(time!=null) {
					return miniOrderMapper.getAllMiniOrderBySiteNameAndTimeList(userId, siteName, pageSize, time);
				}else {
					return miniOrderMapper.getAllMiniOrderBySiteNameList(userId,siteName,pageSize);
				}
			}
		}else if (type==1) {
			if(siteName ==null) {
				if(time!=null) {
					return miniOrderMapper.getMiniOrderByUserIdAndTimeList(0,userId,pageSize, time);
				}else {
					return miniOrderMapper.getMiniOrderByUserIdList(0,userId,pageSize);
				}
			}else {
				if(time!=null) {
					return miniOrderMapper.getMiniOrderBySiteNameAndTimeList(0, userId, siteName,pageSize, time);
				}else {
					return miniOrderMapper.getMiniOrderBySiteNameList(0, userId, siteName,pageSize);
				}
			}
		}else if (type==2) {
			if(siteName ==null) {
				if(time!=null) {
					return miniOrderMapper.getMiniOrderByUserIdAndTimesList(5,userId,pageSize, time);
				}else {
					return miniOrderMapper.getMiniOrderByUserIdList(5,userId,pageSize);
				}
			}else {
				if(time!=null) {
					return miniOrderMapper.getMiniOrderBySiteNameAndTimesList(5, userId, siteName,pageSize, time);
				}else {
					return miniOrderMapper.getMiniOrderBySiteNameList(5, userId, siteName,pageSize);
				}
			}
		}
		return null;
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		return miniOrderMapper.updateStatus(id, status);
	}

	@Override
	public int updateMiniOrderByOrder(String orderNo, Integer fromStatus,Integer toStatus) {
		return miniOrderMapper.updateMiniOrderByOrder(orderNo, fromStatus, toStatus);
	}

	@Override
	public MiniOrder getOrderDetailByOrderNoAndSkuId(String orderNo, Long productEntityId,Long userId) {
		return miniOrderMapper.getOrderDetailByOrderNoAndSkuId(orderNo, productEntityId,userId);
	}

	@Override
	public int updateMiniOrderById(Long id, Integer fromStatus, Integer toStatus) {
		return miniOrderMapper.updateMiniOrderById(id, fromStatus, toStatus);
	}


}
