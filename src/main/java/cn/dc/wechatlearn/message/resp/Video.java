package cn.dc.wechatlearn.message.resp;
/**
 * 视频model
 * @author Administrator
 *
 */
public class Video {
	private String MediaId;//媒体文件id
	
	private String  ThumbMediaId;//缩略图的媒体id

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	
}
