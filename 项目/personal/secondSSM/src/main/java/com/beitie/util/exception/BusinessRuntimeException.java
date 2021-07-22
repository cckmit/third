package com.beitie.util.exception;

public class BusinessRuntimeException  extends RuntimeException{
    private String code;
    private String msg;
    private ResultCode resultCode;
    public  BusinessRuntimeException(){
        super();
    }
    public BusinessRuntimeException(String code,String msg){
        this.msg=msg;
        this.code=code;
    }
    public  BusinessRuntimeException(ResultCode resultCode){
        this.code=resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.resultCode = resultCode;
    }
    public static BusinessRuntimeException newInstance(ResultCode resultCode){
        return new BusinessRuntimeException(resultCode);
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
