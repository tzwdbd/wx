package com.github.binarywang.demo.wechat.exception;


public enum ProcessStatusCode {

	PROCESS_SUCCESS("100","处理成功");
	
	private final String code;
    private final String codeMessage;
    
    public String getCode() {
        return code;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    ProcessStatusCode(String code, String codeMessage) {
        this.code = code;
        this.codeMessage = codeMessage;
    }
    
}
