package cn.dc.wechatlearn.message.resp;

import java.util.HashMap;
import java.util.Map;

public class RespTextMessage {

    private String touser;
    private String msgtype;
    private Map<String, String> text = new HashMap<>();
    public static String success = "success";
    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }
}
