package com.okracode.wechat.offiaccount.helper.repository.dao;

import com.okracode.wechat.offiaccount.helper.repository.entity.WechatMsg;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
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

    @Test
    public void testInsertOneRecvMsg1() throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(2);
        new Thread(()->{
            for (int i = 0; i <= 100; i+=2) {
                textMessageExtDao.selectOneRecvMsg1(i);
            }
            latch.countDown();
        }).start();
        new Thread(()->{
            for (int i = 1; i <= 100; i+=2) {
                textMessageExtDao.selectOneRecvMsg1(i);
            }
            latch.countDown();
        }).start();
        latch.await();
    }
}