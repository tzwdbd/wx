package com.github.binarywang.demo.wechat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import sun.misc.BASE64Decoder;



@SuppressWarnings("restriction")
public class ThreeDES {

	
	public static final byte[] keybyte = "CbvYYHo2oWGY1cKiytlijweK".getBytes();
    private static final String Algorithm = "DESede";  //定义 加密算法,可用 DES,DESede,Blowfish
    private static Log log = LogFactory.getLog(ThreeDES.class);
    
    @SuppressWarnings("restriction")
	public ThreeDES(){
    	Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }
 
    // 加密字符串
    @SuppressWarnings("restriction")
	public static String encryptMode(byte[] src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return new sun.misc.BASE64Encoder().encode(c1.doFinal(src));
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
 
    // 解密字符串
	public static String decryptMode(String src) {
        try { // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            
            return new String(c1.doFinal(new BASE64Decoder().decodeBuffer(src)));
            
        } catch (java.security.NoSuchAlgorithmException e1) {
            log.error("ERROR IN encryptMode cookie1="+src,e1);
        } catch (javax.crypto.NoSuchPaddingException e2) {
            log.error("ERROR IN encryptMode cookie2="+src,e2);
        } catch (java.lang.Exception e3) {
            log.error("ERROR IN encryptMode cookie3="+src,e3);
        }
        return null;
    }
 
    public static void main(String[] args) throws UnsupportedEncodingException{ // 添加新安全算法,如果用JCE就要把它添加进去

//        System.out.println(ThreeDES.encryptMode("haihu2015".getBytes("UTF-8")));
//        System.out.println(ThreeDES.decryptMode("MT7iQGrcIzy/Eevn9TroIQ=="));
    }
}
