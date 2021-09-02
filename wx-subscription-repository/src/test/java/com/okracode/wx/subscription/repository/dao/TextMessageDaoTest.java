package com.okracode.wx.subscription.repository.dao;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Eric Ren
 * @date 2021/8/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TextMessageDaoTest {

    @Resource
    private TextMessageDao textMessageDao;
    @Resource
    private TextMessageExtDao textMessageExtDao;

    @Test
    public void testInsertOneRecvMsg() {
        WechatMsg wechatMsg = WechatMsg.builder()
                .fromUserName("from_user")
                .toUserName("to_user")
                .msgType("text")
                .msgTime(new Date())
                .content(RandomStringUtils.randomAlphabetic(10))
                .build();
        textMessageDao.insertOneRecvMsg(wechatMsg);
        WechatMsg resultMsg = textMessageExtDao.selectOneRecvMsg();
        Assert.assertEquals(wechatMsg.getFromUserName(), resultMsg.getFromUserName());
        Assert.assertEquals(wechatMsg.getContent(), resultMsg.getContent());
    }
}