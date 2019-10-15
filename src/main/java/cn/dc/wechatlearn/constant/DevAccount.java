package cn.dc.wechatlearn.constant;

public enum DevAccount {
    addID("wx899a8e96c179f895"),appsecret("e97614402dfc9e319a15c0d60c37bb78"),openID("o4BkOxBfz4nJPOVfzRCY1WFICSV4");
    private String value;
    DevAccount(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }

}
