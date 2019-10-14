package cn.dc.wechattest.controller;

import cn.dc.wechattest.message.req.TextMessage;
import cn.dc.wechattest.message.resp.RespTextMessage;
import cn.dc.wechattest.util.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.UnsupportedEncodingException;
import java.util.Date;

@RestController
public class PageController {
    @RequestMapping("")
    public String test(@RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       HttpServletRequest request) throws IOException {

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {

            return respXMLMsg(request);
        }
        return "发送失败";
    }

    private String respXMLMsg(HttpServletRequest request) throws UnsupportedEncodingException {
        String postStr = null;
        String resultStr = null;
        try {
            postStr = this.readStreamParameter(request.getInputStream());
            postStr = new String(postStr.getBytes("gbk"), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != postStr && !postStr.isEmpty()) {
            Document document = null;
            try {
                document = DocumentHelper.parseText(postStr);
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
            String time = new Date().getTime() + "";
            String textTpl = "<xml>"
                    + "<ToUserName><![CDATA[%1$s]]></ToUserName>"
                    + "<FromUserName><![CDATA[%2$s]]></FromUserName>"
                    + "<CreateTime>%3$s</CreateTime>"
                    + "<MsgType><![CDATA[%4$s]]></MsgType>"
                    + "<Content><![CDATA[%5$s]]></Content>"
                    + "<FuncFlag>0</FuncFlag>" + "</xml>";

            String msgType = "text";//只写了文字回复，代扩展
            String contentStr = "success";
            resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);
            try {
                resultStr = new String(resultStr.getBytes("UTF-8"), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return resultStr;
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

    public String readStreamParameter(ServletInputStream in) {
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

    private String toJSONString(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        String resutStr = xmlSerializer.read(xml).toString(0);//0表示去除换行空格等，1以上表示json格式化后的数据
        return resutStr;
    }
}
