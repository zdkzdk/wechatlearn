package cn.dc.wechatlearn.sbootcomponent;

import cn.dc.wechatlearn.constant.DevAccount;
import cn.dc.wechatlearn.util.CommonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 项目启动后每7200秒执行一次
 *      微信服务器是7200秒刷新一次accessToken，刷新过程中，5分钟内新老accessToken都可以用
 */
@Component
public class AccessTokenScheduler {
    /**
     * 定时任务方法
     * @Scheduled:设置定时任务
     * cron 属性： cron 表达式。
     */
    @Scheduled(fixedRate = 7200000)
    public void scheduledMethod() throws IOException {
        System.out.println("执行一次CommonUtil.queryAndWrite");
        CommonUtil.queryAndWrite(DevAccount.addID.getValue(), DevAccount.appsecret.getValue());
    }
}
