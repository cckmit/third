package com.beitie.util.exception;

public enum  ResultCode {
    SUCCESS("0x0102001","登录成功！"),
    BUSINESS_SUCCESS("0x0102002","业务办理成功！"),
    PASSWORD_ERROR("0x0102003","密码错误！"),
    USER_NOTFOUND("0x0102004","用户找不到！"),
    UNKNOWN_ERROR("0x0102005","未知错误！");

    private String code;
    private String msg;
    ResultCode() {

    }
    ResultCode(String code,String msg){
        this.code=code;
        this.msg=msg;
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
}
