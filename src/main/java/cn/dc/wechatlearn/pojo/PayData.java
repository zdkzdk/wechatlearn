package cn.dc.wechatlearn.pojo;

public class PayData {
	private String appid;
	private String timestamp;
	private String nonceStr;
	private String packages;
	private String sign;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public PayData(String appid, String timestamp, String nonceStr,
			String packages, String sign) {
		super();
		this.appid = appid;
		this.timestamp = timestamp;
		this.nonceStr = nonceStr;
		this.packages = packages;
		this.sign = sign;
	}
	public PayData() {
		super();
	}
	
}
