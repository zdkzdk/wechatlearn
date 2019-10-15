package cn.dc.wechatlearn.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
    private static String token = "zdk";
    public static boolean checkSignature(String signature,String timestamp,String nonce) {
        //将token timestamp nonce三个参数进行字典排序
        String [] arr = new String[]{token,timestamp,nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            //将三个字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //将sha1加密后的字符串与signature进行对比 标识该请求来源于微信
        return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
    }
    /**
     * 将字节数组转换为字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "" ;
        for(int i = 0 ; i<byteArray.length;i++){
            strDigest+=byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    /**
     * 将字节转换为字符串
     * @param mybyte
     * @return
     */
    private static String byteToHexStr(byte mybyte) {
        char[] Digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mybyte >>> 4) & 0X0F];
        tempArr[1] = Digit[mybyte & 0X0F];
        String str = new String(tempArr);
        return str;
    }
}
