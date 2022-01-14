package com.zb.bean;

import java.util.Date;

public class SsjyxxLog {
    private String id;
    private String clientip;
    private String clientPara;
    private String clientSign;
    private Date clientReqTime;
    private String resultCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getClientPara() {
        return clientPara;
    }

    public void setClientPara(String clientPara) {
        this.clientPara = clientPara;
    }

    public String getClientSign() {
        return clientSign;
    }

    public void setClientSign(String clientSign) {
        this.clientSign = clientSign;
    }

    public Date getClientReqTime() {
        return clientReqTime;
    }

    public void setClientReqTime(Date clientReqTime) {
        this.clientReqTime = clientReqTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return "SsjyxxLog{" +
                "id='" + id + '\'' +
                ", clientip='" + clientip + '\'' +
                ", clientPara='" + clientPara + '\'' +
                ", clientSign='" + clientSign + '\'' +
                ", clientReqTime=" + clientReqTime +
                ", resultCode='" + resultCode + '\'' +
                '}';
    }
}
