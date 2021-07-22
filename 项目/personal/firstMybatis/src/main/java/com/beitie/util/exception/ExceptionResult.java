package com.beitie.util.exception;

public class ExceptionResult {
    private String code;
    private String msg;
    private ResultCode resultCode;

    public ExceptionResult(){

    }
    public ExceptionResult(String code){

    }
    public ExceptionResult(String code,String msg){
        this.code = code;
        this.msg=msg;
    }
    public ExceptionResult(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg=resultCode.getMsg();
        this.resultCode = resultCode;
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
