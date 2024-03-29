package cn.dc.wechatlearn.message.resp;

import java.util.List;

/**
 * 图文消息
 * @author Administrator
 *
 */
public class NewsMessage extends BaseMessage{
	private int ArticleCount; //图文消息个数  限制为10条以内
	
	private List<Article> Articles;//多条图文消息 默认第一个为大图

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
}
