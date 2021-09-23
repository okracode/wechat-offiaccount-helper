package com.okracode.wechat.offiaccount.helper.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.okracode.wechat.offiaccount.helper.common.JsonUtil;
import com.okracode.wechat.offiaccount.helper.common.enums.ChatBotTypeEnum;
import com.okracode.wechat.offiaccount.helper.repository.entity.WechatMsg;
import com.okracode.wechat.offiaccount.helper.repository.entity.receive.RecvTextMessage;
import com.okracode.wechat.offiaccount.helper.repository.entity.send.Article;
import com.okracode.wechat.offiaccount.helper.repository.entity.send.SendNewsMessage;
import com.okracode.wechat.offiaccount.helper.repository.entity.send.SendTextMessage;
import com.okracode.wechat.offiaccount.helper.service.chatbot.ChatBotApiService;
import com.okracode.wechat.offiaccount.helper.service.util.ParseJson;
import com.soecode.wxtools.api.WxConsts;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 * @author Eric Ren
 * @ClassName: TextService
 * @Description: 文字服务类
 * @date May 8, 2017
 */
@Service
@Slf4j
public class TextService {

    private volatile LinkedHashSet<ChatBotApiService> sortedChatBotApi = Sets.newLinkedHashSet();
    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    public TextService(List<ChatBotApiService> chatBotApiServiceList) {
        if (Objects.nonNull(chatBotApiServiceList)) {
            sortedChatBotApi.addAll(chatBotApiServiceList);
        }
    }

    /**
     * 根据消息内容返回对应的消息值
     *
     * @param recvTextMessage
     * @return
     */
    public String processMsg(RecvTextMessage recvTextMessage) {
        try {
            applicationContext.publishEvent(convertWechatMsg(recvTextMessage));
            log.debug("成功放入消息队列请求数据");
        } catch (Exception e) {
            log.error("无法将数据加入到消息队列中", e);
        }
        Integer chatBotType = null;
        String recvContent = recvTextMessage.getContent();
        // 回复文本消息
        SendTextMessage textMessage = new SendTextMessage();
        textMessage.setToUserName(recvTextMessage.getFromUserName());
        textMessage.setFromUserName(recvTextMessage.getToUserName());
        textMessage.setCreateTime(new Date());
        textMessage.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
        textMessage.setFuncFlag(0);
        textMessage.setContent("您发送的是文本消息！");

        // 判断消息内容是否为请求图文回复
        // 创建图文消息
        SendNewsMessage newsMessage = new SendNewsMessage();
        newsMessage.setToUserName(recvTextMessage.getFromUserName());
        newsMessage.setFromUserName(recvTextMessage.getToUserName());
        newsMessage.setCreateTime(new Date());
        newsMessage.setMsgType(WxConsts.CUSTOM_MSG_NEWS);
        newsMessage.setFuncFlag(0);

        List<Article> articleList = new ArrayList<Article>();

        // 判断消息内容是否为单个QQ表情，如果是则原样回复
        if (isQqFace(recvContent)) {
            textMessage.setContent(recvContent);
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
        }
        // 判断是否请求天气预报
        else if (StringUtils.endsWith(recvContent, "天气")) {
            String cityName = "南京";
            if (!"天气".equals(recvContent)) {
                cityName = StringUtils.substringBefore(recvContent, "天气");
            }
            // 根据城市名称获取城市编码
            String cityCode = ParseJson.cityCodeMap.get(cityName);
            textMessage.setContent("无法取得当期城市天气");
            if (!Strings.isNullOrEmpty(cityCode)) {
                // 测试获取实时天气2(包含天气，温度范围)
                Map<String, Object> map2 = Maps.newHashMap();
                try {
                    map2 = getTodayWeather(cityCode);
                } catch (NullPointerException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                textMessage.setContent("城市:" + map2.get("city") + "\t" + "低温:" + map2.get("temp1")
                        + "\t" + "高温:" + map2.get("temp2") + "\t" + "天气:" + map2.get("weather")
                        + "\t" + "发布时间:" + map2.get("ptime"));
            }
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
        } else {
            // 如果都不符合使用默认的转换,则直接调用图灵机器人完成
            String result = null;
            LinkedHashSet<ChatBotApiService> tempSortedChatBotApi = Sets.newLinkedHashSet();
            for (ChatBotApiService chatBotApiService : sortedChatBotApi) {
                log.info("调用的机器人名称：" + chatBotApiService.getClass().getSimpleName());
                result = chatBotApiService.callOpenApi(recvContent);
                if (Objects.nonNull(result)) {
                    chatBotType = Optional.ofNullable(chatBotApiService.getChatBotType()).map(ChatBotTypeEnum::getTypeCode).orElse(null);
                    tempSortedChatBotApi.add(chatBotApiService);
                    break;
                }
            }
            tempSortedChatBotApi.addAll(sortedChatBotApi);
            sortedChatBotApi = tempSortedChatBotApi;
            // 所有机器人都无法处理，使用默认话术
            if (Objects.isNull(result)) {
                result = "对不起，你说的话真是太高深了……";
            }
            textMessage.setContent(result);
        }

        try {
            WechatMsg wechatMsg = WechatMsg.builder()
                    .toUserName(recvTextMessage.getFromUserName())
                    .fromUserName(recvTextMessage.getToUserName())
                    .msgTime(textMessage.getCreateTime())
                    .chatBotType(chatBotType)
                    .msgType(WxConsts.CUSTOM_MSG_TEXT)
                    .content(textMessage.getContent())
                    .funcFlag(null)
                    .msgId(recvTextMessage.getMsgId())
                    .build();
            if (isHelp(recvContent)) {
                wechatMsg.setContent("申请帮助菜单");
            }
            applicationContext.publishEvent(wechatMsg);
            log.debug("成功放入消息队列响应数据");
        } catch (Exception e) {
            log.error("无法将数据加入到消息队列中", e);
        }

        return textMessage.getContent();
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

    /**
     * 获取实时天气2<br> 方 法 名： getTodayWeather <br>
     *
     * @param Cityid 城市编码
     */
    public static Map<String, Object> getTodayWeather(String Cityid) throws IOException,
            NullPointerException {
        // 连接中央气象台的API
        URL url = new URL("http://www.weather.com.cn/data/cityinfo/" + Cityid + ".html");
        URLConnection connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(connectionData.getInputStream(),
                            "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String datas = sb.toString();
            System.out.println(datas);
            JsonNode jsonNode = JsonUtil.getNode(datas);
            return JsonUtil.convert2Map(jsonNode.findValue("weatherinfo").toString());
        } catch (SocketTimeoutException e) {
            log.error("连接超时", e);
        } catch (FileNotFoundException e) {
            log.error("加载文件出错", e);
        }
        return Maps.newHashMap();

    }

    private static WechatMsg convertWechatMsg(RecvTextMessage msg) {
        return WechatMsg.builder()
                .toUserName(msg.getToUserName())
                .fromUserName(msg.getFromUserName())
                .msgTime(msg.getCreateTime())
                .chatBotType(null)
                .msgType(msg.getMsgType())
                .content(msg.getContent())
                .funcFlag(null)
                .msgId(msg.getMsgId())
                .build();
    }
}
