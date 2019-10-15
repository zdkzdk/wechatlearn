package cn.dc.wechatlearn.controller;

import cn.dc.wechatlearn.bean.menu.Menu;
import cn.dc.wechatlearn.constant.DevAccount;
import cn.dc.wechatlearn.message.req.TextMessage;
import cn.dc.wechatlearn.message.resp.Article;
import cn.dc.wechatlearn.message.resp.NewsMessage;
import cn.dc.wechatlearn.message.resp.RespTextMessage;
import cn.dc.wechatlearn.util.CommonUtil;
import cn.dc.wechatlearn.util.MenuUtil;
import cn.dc.wechatlearn.util.MessageUtil;
import cn.dc.wechatlearn.util.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * protal控制器，用来接收移动端请求
 */
@RestController
public class MainController {
    @RequestMapping("")
    public String dispatcher(@RequestParam String signature,
                             @RequestParam String timestamp,
                             @RequestParam String nonce,
                             @RequestParam(required = false) String echostr,
                             HttpServletRequest request) throws IOException {
        /*
        判断发送请求的公众号是否合法
            执行响应用户消息的方法
            回复信息不合法！！！
         */
        return echostr == null && SignUtil.checkSignature(signature, timestamp, nonce) ? handleMsg(request) : echostr;

    }

    /**
     * 处理用户发送的消息并生成响应的xml
     */
    private String handleMsg(HttpServletRequest request) throws IOException {
        String reqBodyStr = null;
        String resultStr = null;
        try {
            reqBodyStr = this.readStreamParameter(request.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        如果reqBodyStr不为空
            进行xml解析
         */
        if (null != reqBodyStr && !reqBodyStr.isEmpty()) {
            Document document = null;
            try {
                document = DocumentHelper.parseText(reqBodyStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == document) {
                return "";
            }
            Element root = document.getRootElement();
            String fromUsername = root.elementText("FromUserName");
            String toUsername = root.elementText("ToUserName");
            String event = root.elementText("Event");
            String key = root.elementText("EventKey");
            String keyword = root.elementTextTrim("Content");
            /*
            解析完，根据用户输入的content和event进行业务处理
             */
            String contentStr = null;
            String access_token = CommonUtil.getToken(DevAccount.addID.getValue(), DevAccount.appsecret.getValue()).getAccessToken();
            if (null != keyword && !keyword.equals("")) {
                switch (keyword) {
                     /*
                        菜单处理
                     */
                    case "创建菜单":
                        MenuUtil.createMenu(new Menu(), access_token);
                        return respXMLMesg(fromUsername, toUsername, "菜单创建");
                    case "查询菜单":
                        return respXMLMesg(fromUsername, toUsername, MenuUtil.getMenu(access_token));
                    case "删除菜单":
                        MenuUtil.deleteMenu(access_token);
                        return respXMLMesg(fromUsername, toUsername, "菜单删除");
                    /*
                    文本命令
                     */
                    case "点餐":
                        Article article = new Article();
                        article.setTitle("嘿！你的工作餐。 是你的工作餐！！");
                        article.setDescription("点击进行点餐");
                        article.setPicUrl("http://upimg.zixun.3158.cn/7/2012/1106/13521949886390.jpg");
                        // article.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8aa7739e469338df&redirect_uri=http://cdata.tunnel.mobi/myweixin/oauthServlet&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
                        article.setUrl("http://cdata.tunnel.mobi/weixinTest/oauthAction");
                        List<Article> articleList = new ArrayList<Article>();
                        articleList.add(article);
                        // 创建图文消息
                        NewsMessage newsMessage = new NewsMessage();
                        newsMessage.setToUserName(fromUsername);
                        newsMessage.setFromUserName(toUsername);
                        newsMessage.setCreateTime(new Date().getTime());
                        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        newsMessage.setArticleCount(articleList.size());
                        newsMessage.setArticles(articleList);
                        String respXml = MessageUtil.messageToXml(newsMessage);
                        return respXml;
                }
            } else {
                 /*
                    事件处理
                */
                if (event != null){
                    switch (event){
                        case "CLICK":
                            String url = null;
                            JSONObject jsonObject = null;
                            switch (key) {
                                case "phone":
                                    contentStr = "请拨打4000-000-000！";
                                    break;
                                case "userInfo":
                                    url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
                                    url = url.replace("ACCESS_TOKEN", access_token).replace("OPENID", fromUsername);
                                    jsonObject = CommonUtil.httpsRequest(url, "GET", null);
                                    contentStr = jsonObject.toString();
                                    break;
                                case "userList":
                                    url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
                                    url = url.replace("ACCESS_TOKEN", access_token).replace("NEXT_OPENID", "");
                                    jsonObject = CommonUtil.httpsRequest(url, "GET", null);
                                    contentStr = jsonObject.toString();
                                    break;
                            }
                            break;
                        case "subscribe":
                            contentStr = "您好，欢迎关注！体验生活，从这里开始！";
                            break;
                        case "SCAN":
                            contentStr = "您好，您已关注！";
                            break;
                        case "unsubscribe":
                            contentStr = "您好，您已取消关注！";
                            break;
                        case "VIEW":

                            break;
                    }
                }
                return respXMLMesg(fromUsername, toUsername, contentStr);
            }
            return respXMLMesg(fromUsername, toUsername, "success");
        }
        return "";
    }

    /**
     * 从请求体的流对象中读取xml
     * @param in
     */
    private String readStreamParameter(ServletInputStream in) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }

    /*
    从请求中获取xml的字符串，转成json字符串，再转成对象
    然后拼接responseMesg，响应回
     */
    private RespTextMessage respJsonMessage(HttpServletRequest request) throws IOException {

        String postStr = new String(readStreamParameter(request.getInputStream()).getBytes("gbk"), "UTF-8");
        String jsonStr = this.toJSONString(postStr);
        /*
        使用json
         */
        ObjectMapper mapper = new ObjectMapper();
        TextMessage message = mapper.readValue(jsonStr, TextMessage.class);
        RespTextMessage respTextMessage = new RespTextMessage();
        respTextMessage.setTouser(message.getFromUserName());
        respTextMessage.setMsgtype(message.getMsgType());
        respTextMessage.getText().put("content", "success");
        return respTextMessage;
    }

    /**
     * xml转json
     * @param xml
     */
    private String toJSONString(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        String resutStr = xmlSerializer.read(xml).toString(0);//0表示去除换行空格等，1以上表示json格式化后的数据
        return resutStr;
    }

    /**
     *  将要返回的信息封装为xml
     */
    private String respXMLMesg(String fromUsername, String toUsername, String contentStr) {
        String time = new Date().getTime() + "";
        String textTpl = "<xml>"
                + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                + "<CreateTime>%3$s</CreateTime>"
                + "<MsgType><![CDATA[%4$s]]></MsgType>"
                + "<Content><![CDATA[%5$s]]></Content>"
                + "<FuncFlag>0</FuncFlag>" + "</xml>";

        String msgType = "text";//只写了文字回复，待扩展
        return textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
    }
}
