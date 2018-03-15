package com.github.binarywang.demo.wechat.utils;

import java.math.BigDecimal;

public class PriceUtils {
	
	/**
	 * 分转成元
	 * @param rmb_fen
	 * @return
	 */
	public static String fen2Yuan(Integer fen) {
		 BigDecimal b1 = new BigDecimal(fen);
		 BigDecimal b2 = new BigDecimal(100);
		 int scale =2;
		 return String.valueOf(b1.divide(b2, scale, BigDecimal.ROUND_CEILING));
	}
	
	/**
	 * 元转出分
	 * @param rmb_fen
	 * @return
	 */
	public static String yuan2Fen(String yuan) {
		 BigDecimal b1 = new BigDecimal(yuan);
		 BigDecimal b2 = new BigDecimal("100");
		 return String.valueOf(b1.multiply(b2));
	}
	

}
