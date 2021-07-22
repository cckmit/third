package com.beitie.util.exception;


public class CustomException  extends Exception {
    private String code;
    private String msg;
    private ResultCode resultCode;
    public CustomException(String code,String msg) {
        super(msg);
        this.msg=msg;
        this.code=code;
    }
    public CustomException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.msg=resultCode.getMsg();
        this.code=resultCode.getCode();
        this.resultCode=resultCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
