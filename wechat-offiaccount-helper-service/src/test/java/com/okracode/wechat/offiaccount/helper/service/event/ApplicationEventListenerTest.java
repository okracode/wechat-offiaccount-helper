package com.okracode.wechat.offiaccount.helper.service.event;

import com.okracode.wechat.offiaccount.helper.common.enums.ChatBotTypeEnum;
import com.okracode.wechat.offiaccount.helper.repository.dao.TextMessageExtDao;
import com.okracode.wechat.offiaccount.helper.repository.entity.WechatMsg;
import com.soecode.wxtools.api.WxConsts;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eric Ren
 * @date 2021/8/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationEventListenerTest {

    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private TextMessageExtDao textMessageExtDao;

    @Test
    public void testMsgEventListener() throws InterruptedException {
        WechatMsg wechatMsg = WechatMsg.builder()
                .toUserName(RandomStringUtils.randomAlphabetic(10))
                .fromUserName(RandomStringUtils.randomAlphabetic(10))
                .msgTime(new Date())
                .chatBotType(ChatBotTypeEnum.TU_LING_123.getTypeCode())
                .msgType(WxConsts.CUSTOM_MSG_TEXT)
                .content(RandomStringUtils.randomAlphabetic(10))
                .funcFlag(null)
                .msgId(Long.valueOf(RandomStringUtils.randomNumeric(10)))
                .build();
        applicationContext.publishEvent(wechatMsg);
        TimeUnit.SECONDS.sleep(3);
        WechatMsg resultMsg = textMessageExtDao.selectOneMsgByCondition(wechatMsg);
        Assert.assertEquals(wechatMsg.getFromUserName(), resultMsg.getFromUserName());
        Assert.assertEquals(wechatMsg.getContent(), resultMsg.getContent());
    }
}