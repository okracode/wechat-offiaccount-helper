package com.okracode.wx.subscription.service;

import com.okracode.wx.subscription.common.conf.CommonConfig;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author Eric Ren
 * @ClassName: TulingApiService
 * @Description: 调用图灵机器人api接口，获取智能回复内容
 * @date Apr 12, 2017
 */
@Service
public class TulingApiService {

    /**
     * @Fields LOG : Log4j 日志类
     */
    private static final Logger LOG = Logger.getLogger(TulingApiService.class);

    @Resource
    private CommonConfig commonConfig;

    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     *
     * @param content
     * @return
     */
    public String getTulingResult(String content) {
        /** 此处为图灵api接口，参数key需要自己去注册申请 */
        String apiUrl = commonConfig.getTulingRobot();
        String param = "";
        try {
            param = apiUrl + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            LOG.error(e1);
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
            LOG.error(e);
        } catch (IOException e) {
            LOG.error(e);
        }
        /** 请求失败处理 */
        if (null == result) {
            return "对不起，你说的话真是太高深了……";
        }

        try {
            JSONObject json = new JSONObject(result);
            // 以code=100000为例，参考图灵机器人api文档
            if (100000 == json.getInt("code")) {
                result = json.getString("text");
            }
        } catch (JSONException e) {
            LOG.error(e);
        }
        return result;
    }
}
