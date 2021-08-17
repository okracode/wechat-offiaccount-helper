package com.okracode.wx.subscription.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.okracode.wx.subscription.service.util.ParseJson;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eric Ren
 * @date 2021/8/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CoreControllerTest {

    @BeforeClass
    public static void init() {
        try {
            // 读取城市编码文件
            ParseJson.parseJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoGet() throws IOException {
        String signature = "859ca01fc42225b90a8024aadce883a8be5f4820";
        String timestamp = "1629190501";
        String nonce = "553491588";
        String echostr = "1556285846770679653";
        String url = "http://localhost:8080/wechat.do?signature=%s&timestamp=%s&nonce=%s&echostr=%s";
        String formatUrl = String.format(url, signature, timestamp, nonce, echostr);
        HttpGet request = new HttpGet(formatUrl);
        HttpResponse response = HttpClients.createDefault().execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        String resEchoStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        assertTrue(StringUtils.isNotBlank(resEchoStr));
        assertEquals(echostr, resEchoStr);
    }

    @Test
    public void testDoPost() throws IOException {
        String url = "http://localhost:8080/wechat.do";
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        HttpEntity entity = new StringEntity("<xml>\n"
                + "<ToUserName>1234</ToUserName>\n"
                + "<FromUserName>233</FromUserName>\n"
                + "<CreateTime>1628732742</CreateTime>\n"
                + "<MsgType>text</MsgType>\n"
                + "<MsgId>99991234</MsgId>\n"
                + "<Content>南京天气</Content>\n"
                + "</xml>", StandardCharsets.UTF_8
        );
        //设置参数到请求对象中
        httpPost.setEntity(entity);

        System.out.println("请求地址：" + url);
        System.out.println("请求参数：" + entity.toString());
        HttpResponse response = HttpClients.createDefault().execute(httpPost);
        assertEquals(200, response.getStatusLine().getStatusCode());
        String resEchoStr = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        assertTrue(StringUtils.contains(resEchoStr, "城市:南京"));
    }
}