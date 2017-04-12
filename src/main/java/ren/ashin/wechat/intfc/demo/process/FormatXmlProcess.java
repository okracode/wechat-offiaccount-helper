package ren.ashin.wechat.intfc.demo.process;

import java.util.Date;

/**
 * @ClassName: FormatXmlProcess
 * @Description: 封装最终的xml格式结果
 * @author renzx
 * @date Apr 12, 2017
 */
public class FormatXmlProcess {
    /**
     * 封装文字类的返回消息
     * 
     * @param to
     * @param from
     * @param content
     * @return
     */
    public String formatXmlAnswer(String to, String from, String content) {
        StringBuffer sb = new StringBuffer();
        Date date = new Date();
        sb.append("<xml><ToUserName><![CDATA[");
        sb.append(to);
        sb.append("]]></ToUserName><FromUserName><![CDATA[");
        sb.append(from);
        sb.append("]]></FromUserName><CreateTime>");
        sb.append(date.getTime());
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
        sb.append(content);
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
        return sb.toString();
    }
}
