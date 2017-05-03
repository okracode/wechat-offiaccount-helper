package ren.ashin.wechat.intfc.demo.process;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;

import ren.ashin.wechat.intfc.demo.entity.ReceiveXmlEntity;

/**
 * @ClassName: WechatProcess
 * @Description: 微信xml消息处理流程逻辑类
 * @author renzx
 * @date Apr 12, 2017
 */
public class WechatProcess {
    private static final Logger LOG = Logger.getLogger(WechatProcess.class);

    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     * 
     * @param xml 接收到的微信数据
     * @return 最终的解析结果（xml格式数据）
     */
    public String processWechatMag(String xml) {
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        LOG.info("xmlEntity:" + xmlEntity.toString());
        /** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
        String result = "";
        if ("text".endsWith(xmlEntity.getMsgType())) {
            // 对文本信息进行加工处理
            String content = xmlEntity.getContent();
            String fromUserName = xmlEntity.getFromUserName();
            if (!Strings.isNullOrEmpty(content)) {
                result = handleTextMsg(content, fromUserName);
            } else {
                LOG.warn("收到了空字符串");
                result = "收到了无法处理的字符串:" + content;
            }
        }

        /**
         * 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
         * */
        result =
                new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(),
                        xmlEntity.getToUserName(), result);

        return result;
    }

    private String handleTextMsg(String content, String fromUserName) {
        // 首先要判断用户内容是否需要单独处理
        String[] searchStrs = {"扇贝绑定", "扇贝解绑", "扇贝排行榜"};
        String result = "";
        if (StringUtils.startsWithAny(content, searchStrs)) {
            result = "扇贝测试";
        } else {
            result = new TulingApiProcess().getTulingResult(content);
        }
        return result;
    }
}
