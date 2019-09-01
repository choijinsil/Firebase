package com.korea.my_chat_app;

public class ChatBean {
    private String userNm;
    private String msg;

    public ChatBean(String userNm, String msg) {
        this.userNm = userNm;
        this.msg = msg;
    }

    public ChatBean() {

    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ChatBean{" +
                "userNm='" + userNm + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
