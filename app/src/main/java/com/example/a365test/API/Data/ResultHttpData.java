package com.example.a365test.API.Data;

public class ResultHttpData {
    private int tag = 0;
    private int rtnCode = 0;
    private String rtnDataString = "";
    private int type = 0;
    private Object rtnData = null;
    private int action = 0;
    private String actionName = null;
    private long respTime = 0;
    private String errPage = null;

    // Getter and Setter methods
    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(int rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnDataString() {
        return rtnDataString;
    }

    public void setRtnDataString(String rtnDataString) {
        this.rtnDataString = rtnDataString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getRtnData() {
        return rtnData;
    }

    public void setRtnData(Object rtnData) {
        this.rtnData = rtnData;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public long getRespTime() {
        return respTime;
    }

    public void setRespTime(long respTime) {
        this.respTime = respTime;
    }

    public String getErrPage() {
        return errPage;
    }

    public void setErrPage(String errPage) {
        this.errPage = errPage;
    }
}
