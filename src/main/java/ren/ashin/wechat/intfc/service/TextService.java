package ren.ashin.wechat.intfc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ren.ashin.wechat.intfc.WeChatServer;
import ren.ashin.wechat.intfc.bean.recv.RecvTextMessage;
import ren.ashin.wechat.intfc.bean.send.Article;
import ren.ashin.wechat.intfc.bean.send.SendNewsMessage;
import ren.ashin.wechat.intfc.bean.send.SendTextMessage;
import ren.ashin.wechat.intfc.util.MessageUtil;


/**
 * @ClassName: TextService
 * @Description: 文字服务类
 * @author renzx
 * @date May 8, 2017
 */
public class TextService {
    private static final Logger LOG = Logger.getLogger(TextService.class);

    /**
     * 根据消息内容返回对应的消息值
     * 
     * @param recvTextMessage
     * @param respContent
     * @return
     */
    public static String processMsg(RecvTextMessage recvTextMessage) {
        String respMessage = null;
        String recvContent = recvTextMessage.getContent();
        // 回复文本消息
        SendTextMessage textMessage = new SendTextMessage();
        textMessage.setToUserName(recvTextMessage.getFromUserName());
        textMessage.setFromUserName(recvTextMessage.getToUserName());
        textMessage.setCreateTime(new Date());
        textMessage.setMsgType(MessageUtil.SEND_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        textMessage.setContent("您发送的是文本消息！");



        // 判断消息内容是否为请求图文回复
        // 创建图文消息
        SendNewsMessage newsMessage = new SendNewsMessage();
        newsMessage.setToUserName(recvTextMessage.getFromUserName());
        newsMessage.setFromUserName(recvTextMessage.getToUserName());
        newsMessage.setCreateTime(new Date());
        newsMessage.setMsgType(MessageUtil.SEND_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);

        List<Article> articleList = new ArrayList<Article>();

        // 判断消息内容是否为单个QQ表情，如果是则原样回复
        if (isQqFace(recvContent)) {
            textMessage.setContent(recvContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }
        // 判断消息内容是否为请求帮助菜单
        else if (isHelp(recvContent)) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("您好，请回复数字选择服务：").append("\n\n");
            buffer.append("1  天气预报").append("\n");
            buffer.append("2  公交查询").append("\n");
            buffer.append("3  周边搜索").append("\n");
            buffer.append("4  歌曲点播").append("\n");
            buffer.append("5  经典游戏").append("\n");
            buffer.append("6  美女电台").append("\n");
            buffer.append("7  人脸识别").append("\n");
            buffer.append("8  聊天唠嗑").append("\n\n");
            buffer.append("回复“?”显示此帮助菜单\n");
            buffer.append("更多精彩内容" + emoji(0x1F47F)
                    + "请访问<a href=\"http://www.ashin.ren\">阿信的博客</a>");
            textMessage.setContent(buffer.toString());
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }

        // 单图文消息
        else if ("1".equals(recvContent)) {
            Article article = new Article();
            article.setTitle("微信公众帐号开发教程Java版");
            article.setDescription("柳峰，80后，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
            article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
            article.setUrl("http://blog.csdn.net/lyq8479");
            articleList.add(article);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        // 单图文消息---不含图片
        else if ("2".equals(recvContent)) {
            Article article = new Article();
            article.setTitle("微信公众帐号开发教程Java版");
            // 图文消息中可以使用QQ表情、符号表情
            article.setDescription("柳峰，80后，"
                    + emoji(0x1F6B9)
                    + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
            // 将图片置为空
            article.setPicUrl("");
            article.setUrl("http://blog.csdn.net/lyq8479");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        // 多图文消息
        else if ("3".equals(recvContent)) {
            Article article1 = new Article();
            article1.setTitle("微信公众帐号开发教程\n引言");
            article1.setDescription("");
            article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
            article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");

            Article article2 = new Article();
            article2.setTitle("第2篇\n微信公众帐号的类型");
            article2.setDescription("");
            article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");

            Article article3 = new Article();
            article3.setTitle("第3篇\n开发模式启用及接口配置");
            article3.setDescription("");
            article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        // 多图文消息---首条消息不含图片
        else if ("4".equals(recvContent)) {
            Article article1 = new Article();
            article1.setTitle("微信公众帐号开发教程Java版");
            article1.setDescription("");
            // 将图片置为空
            article1.setPicUrl("");
            article1.setUrl("http://blog.csdn.net/lyq8479");

            Article article2 = new Article();
            article2.setTitle("第4篇\n消息及消息处理工具的封装");
            article2.setDescription("");
            article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");

            Article article3 = new Article();
            article3.setTitle("第5篇\n各种消息的接收与响应");
            article3.setDescription("");
            article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");

            Article article4 = new Article();
            article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
            article4.setDescription("");
            article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            articleList.add(article4);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        // 多图文消息---最后一条消息不含图片
        else if ("5".equals(recvContent)) {
            Article article1 = new Article();
            article1.setTitle("第7篇\n文本消息中换行符的使用");
            article1.setDescription("");
            article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
            article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");

            Article article2 = new Article();
            article2.setTitle("第8篇\n文本消息中使用网页超链接");
            article2.setDescription("");
            article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
            article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");

            Article article3 = new Article();
            article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
            article3.setDescription("");
            // 将图片置为空
            article3.setPicUrl("");
            article3.setUrl("http://blog.csdn.net/lyq8479");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        } else {
            // 如果都不符合使用默认的转换,则直接调用图灵机器人完成
            String result = TulingApiService.getTulingResult(recvContent);
            textMessage.setContent(result);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        }

        try {
            WeChatServer.queue.put(recvTextMessage);
            // 组一个假的RecvTextMessage暂时方便插入
            RecvTextMessage sendMsg = new RecvTextMessage();
            sendMsg.setMsgId(recvTextMessage.getMsgId());
            sendMsg.setToUserName(recvTextMessage.getFromUserName());
            sendMsg.setFromUserName(recvTextMessage.getToUserName());
            sendMsg.setCreateTime(textMessage.getCreateTime());
            sendMsg.setMsgType(MessageUtil.SEND_MESSAGE_TYPE_TEXT);
            sendMsg.setContent(textMessage.getContent());
            if(isHelp(recvContent)){
                sendMsg.setContent("申请帮助菜单");
            }
            WeChatServer.queue.put(sendMsg);
            LOG.debug("成功放入消息队列一组数据");
        } catch (Exception e) {
            LOG.error("无法将数据加入到消息队列中", e);
        }

        return respMessage;
    }

    /**
     * 判断是否是QQ表情,当前只针对一条消息中存在一个表情时生效
     * 
     * @param content
     * @return
     */
    public static boolean isQqFace(String content) {
        boolean result = false;

        // 判断QQ表情的正则表达式
        String qqfaceRegex =
                "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";
        Pattern p = Pattern.compile(qqfaceRegex);
        Matcher m = p.matcher(content);
        if (m.matches()) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否是申请帮助菜单
     * 
     * @param content
     * @return
     */
    public static boolean isHelp(String content) {
        if ("?".equals(content) || "？".equals(content) || "help".equals(content)) {
            return true;
        }
        return false;
    }

    /**
     * emoji表情转换(hex -> utf-16)
     * 
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }
}
