package com.okracode.wx.subscription.service;

import com.google.common.collect.Maps;
import com.okracode.wx.subscription.repository.dao.TextMessageExtDao;
import com.okracode.wx.subscription.repository.entity.WechatMsg;
import com.soecode.wxtools.api.WxConsts;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutTextMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eric Ren
 * @date 2021/8/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CoreServiceTest {

    @Resource
    private CoreService coreService;
    @Resource
    private TextMessageExtDao textMessageExtDao;

    @Test
    public void testHandle() throws InterruptedException {
        WxXmlMessage wx = new WxXmlMessage();
        wx.setToUserName(RandomStringUtils.randomAlphabetic(10));
        wx.setFromUserName(RandomStringUtils.randomAlphabetic(10));
        wx.setCreateTime(new Date().getTime() / 1000);
        wx.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
        wx.setContent(RandomStringUtils.randomAlphabetic(10));
        wx.setMsgId(Long.valueOf(RandomStringUtils.randomNumeric(10)));
        WxXmlOutTextMessage result = (WxXmlOutTextMessage) coreService.handle(wx, Maps.newHashMap(), null);
        TimeUnit.SECONDS.sleep(3);
        WechatMsg resultMsg = textMessageExtDao
                .selectOneMsgByCondition(WechatMsg.builder().fromUserName(wx.getToUserName()).build());
        Assert.assertEquals(result.getFromUserName(), resultMsg.getFromUserName());
        Assert.assertEquals(result.getContent(), resultMsg.getContent());
    }

    @Test
    public void testHandle_weather() throws InterruptedException {
        WxXmlMessage wx = new WxXmlMessage();
        wx.setToUserName(RandomStringUtils.randomAlphabetic(10));
        wx.setFromUserName(RandomStringUtils.randomAlphabetic(10));
        wx.setCreateTime(new Date().getTime() / 1000);
        wx.setMsgType(WxConsts.CUSTOM_MSG_TEXT);
        wx.setContent("南京天气");
        wx.setMsgId(Long.valueOf(RandomStringUtils.randomNumeric(10)));
        WxXmlOutTextMessage result = (WxXmlOutTextMessage) coreService.handle(wx, Maps.newHashMap(), null);
        TimeUnit.SECONDS.sleep(3);
        WechatMsg resultMsg = textMessageExtDao
                .selectOneMsgByCondition(WechatMsg.builder().fromUserName(wx.getToUserName()).build());
        Assert.assertEquals(result.getFromUserName(), resultMsg.getFromUserName());
        Assert.assertEquals(result.getContent(), resultMsg.getContent());
    }
}