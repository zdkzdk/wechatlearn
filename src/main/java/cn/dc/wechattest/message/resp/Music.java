package cn.dc.wechattest.message.resp;
/**
 * 音乐model
 * @author Administrator
 *
 */
public class Music {
	private String Title;//音乐标题
	
	private String Description;//音乐描述
	
	private String MusicUrl;//音乐链接
	
	private String HQMusicUrl;//高清链接
	
	private String ThumbMediaId;//缩略图的媒体id 通过上传多媒体文件得到的id

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicUrl() {
		return MusicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	
	
}
