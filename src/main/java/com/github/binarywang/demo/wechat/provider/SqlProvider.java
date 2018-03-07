package com.github.binarywang.demo.wechat.provider;

import org.apache.ibatis.jdbc.SQL;

import com.github.binarywang.demo.wechat.bean.User;

public class SqlProvider {

	public String selectUser(User user){  
	       StringBuffer sql = new StringBuffer("select * from oversea.mini_users where 1=1 ");  
	       if(user.getNickName() != null){  
	           sql.append(" and nick_name=#{nicName}");  
	       }  
	       if(user.getStatus() != null){  
	           sql.append(" and status=#{status}");  
	       }  
	       return sql.toString();  
	}
	public String select6(final User user){  
	       return new SQL(){{  
	           SELECT("id,name,email");  
	           FROM("demo");  
	           if(user.getNickName() != null){  
	              WHERE("name=#{name}");  
	           }  
	           if(user.getStatus() != null){  
	              WHERE("email=#{email}");  
	           }  
	       }}.toString();  
	    }  
	
	public String delete2(){  
	       return new SQL(){{  
	           DELETE_FROM("demo");  
	           WHERE("id=#{id}");  
	       }}.toString();  
	    }  
	public String update2(final User user){  
	       return new SQL(){{  
	           UPDATE("demo");  
	        
	           //条件写法.  
	           if(user.getNickName() != null){  
	              SET("name=#{name}");  
	           }  
	           if(user.getStatus() != null){  
	              SET("email=#{email}");  
	           }  
	           WHERE("id=#{id}");  
	       }}.toString();  
	    } 
	
	 public String save3(final User user){  
	       return new SQL(){{  
	           INSERT_INTO("demo");  
	           //多个写法.  
	           INTO_COLUMNS("name","email");  
	           INTO_VALUES("#{name}","#{email}");  
	            
	           //条件写法.  
//	         if(demo.getName() != null){  
//	            VALUES("name","#{name}");  
//	         }  
//	         if(demo.getEmail() != null){  
//	            VALUES("email","#{email}");  
//	         }  
	            
	       }}.toString();  
	    }  
}
