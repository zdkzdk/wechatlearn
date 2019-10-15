	package cn.dc.wechatlearn.util;


    import cn.dc.wechatlearn.bean.menu.Menu;
    import net.sf.json.JSONObject;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    /**
     * 自定义菜单工具类
     *
     */
    public class MenuUtil {
        private static Logger log = LoggerFactory.getLogger(MenuUtil.class);

        // 菜单创建（POST）
        public final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
        // 菜单查询（GET）
        public final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
        // 菜单删除（GET）
        public final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

        /**
         * 创建菜单
         *
         * @param menu 菜单实例
         * @param accessToken 凭证
         * @return true成功 false失败
         */
        public static boolean createMenu(Menu menu, String accessToken) {
            boolean result = false;
            String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
            // 将菜单对象转换成json字符串
            String jsonMenu = JSONObject.fromObject(menu).toString();
            System.out.println(jsonMenu);
            // 发起POST请求创建菜单
            String outputStr = "{\"button\":["
                    + "{\"name\":\"优惠活动\","
                    + "\"sub_button\":["
                    + "{\"type\":\"click\",\"name\":\"最新活动\",\"key\":\"huodong_zuixing\"},"
                    + "{\"type\":\"click\",\"name\":\"优惠券\",\"key\":\"huodong_youhuiquan\"},"
                    + "{\"type\":\"click\",\"name\":\"优惠套餐\",\"key\":\"huodong_youhuitaocan\"}"
                    + "]},"
                    + "{\"type\":\"view\",\"name\":\"转到微网页\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx899a8e96c179f895&redirect_uri=http%3a%2f%2fzdk.free.idcfengye.com%2ftest&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"},"
                    + "{\"name\":\"我的服务\","
                    + "\"sub_button\":["
                    + "{\"type\":\"view\",\"name\":\"我的订单\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5b630ae58e5df9f&redirect_uri=http%3A%2F%2Fwww%2Eieater%2Ecn%2Fwm%2Ffront%2DmyOrder%2Dlist&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"},"
                    + "{\"type\":\"view\",\"name\":\"我的地址\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe5b630ae58e5df9f&redirect_uri=http%3A%2F%2Fwww%2Eieater%2Ecn%2Fwm%2Ffront%2DmyAddr%2Dshow&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\"},"
                    + "{\"type\":\"click\",\"name\":\"我的信息\",\"key\":\"userInfo\"},"
                    + "{\"type\":\"click\",\"name\":\"用户列表\",\"key\":\"userList\"},"
                    + "{\"type\":\"click\",\"name\":\"客服电话\",\"key\":\"phone\"}"
                    + "]}"
                    + "]}";
            JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", outputStr);

            if (null != jsonObject) {
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                if (0 == errorCode) {
                    log.info("创建菜单成功errcode:{} errmsg:{}", errorCode, errorMsg);
                    result = true;
                } else {
                    result = false;
                    log.error("创建菜单失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }

            return result;
        }

        /**
         * 查询菜单
         *
         * @param accessToken 凭证
         * @return
         */
        public static String getMenu(String accessToken) {
            String result = null;
            String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
            // 发起GET请求查询菜单
            JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

            if (null != jsonObject) {
                result = jsonObject.toString();
            }
            return result;
        }

        /**
         * 删除菜单
         *
         * @param accessToken 凭证
         * @return true成功 false失败
         */
        public static boolean deleteMenu(String accessToken) {
            boolean result = false;
            String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
            // 发起GET请求删除菜单
            JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

            if (null != jsonObject) {
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                if (0 == errorCode) {
                    result = true;
                    log.info("删除菜单成功 errcode:{} errmsg:{}", errorCode, errorMsg);
                } else {
                    result = false;
                    log.error("删除菜单失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                }
            }
            return result;
        }
    }