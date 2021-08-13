package com.okracode.wx.subscription.service.chatbot.impl;

import com.okracode.wx.subscription.common.conf.CommonConfig;
import com.okracode.wx.subscription.service.chatbot.ChatBotApiService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 图灵聊天机器人
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/13
 */
@Service
@Slf4j
@Order(1)
public class Tuling123ApiServiceImpl implements ChatBotApiService {

    @Resource
    private CommonConfig commonConfig;

    @Override
    public String callOpenApi(String content) {
        /** 此处为图灵api接口，参数key需要自己去注册申请 */
        String apiUrl = commonConfig.getTulingRobot();
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            log.error("encode exp", e1);
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
        if(StringUtils.isBlank(result)) {
            return null;
        }

        try {
            JSONObject json = new JSONObject(result);
            // 以code=100000为例，参考图灵机器人api文档
            if (100000 == json.getInt("code")) {
                return json.getString("text");
            } else {
                log.error("无效的返回码,{}", json);
            }
        } catch (JSONException e) {
            log.error("json exp", e);
        }
        return null;
    }
}
