package com.okracode.wx.subscription.service.chatbot.impl;

import com.google.common.base.Objects;
import com.okracode.wx.subscription.common.JsonUtil;
import com.okracode.wx.subscription.common.enums.ChatBotTypeEnum;
import com.okracode.wx.subscription.service.chatbot.ChatBotApiService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 青云客聊天机器人
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/13
 */
@Service
@Slf4j
@Order(2)
public class QingyunkeApiServiceImpl implements ChatBotApiService {

    @Override
    public String callOpenApi(String content) {
        // 青云客固定url
        String apiUrl = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("encode exp", e);
        } // 将参数转为url编码

        /** 发送httpget请求 */
        HttpGet request = new HttpGet(param);
        String result = "";
        try {
            HttpResponse response = HttpClients.createDefault().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (ClientProtocolException e) {
            log.error("protocol exp", e);
        } catch (IOException e) {
            log.error("io exp", e);
        }
        if (StringUtils.isBlank(result)) {
            return null;
        }

        try {
            Map<String, Object> json = JsonUtil.convert2Map(result);
            // 以code=100000为例，参考图灵机器人api文档
            if (Objects.equal(0, json.get("result"))) {
                return json.get("content").toString();
            } else {
                log.error("无效的返回码,{}", json);
            }
        } catch (Exception e) {
            log.error("json exp", e);
        }
        return null;
    }

    @Override
    public ChatBotTypeEnum getChatBotType() {
        return ChatBotTypeEnum.QING_YUN_KE;
    }
}
