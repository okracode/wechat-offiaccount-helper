package com.okracode.wx.subscription.repository.dao;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import java.util.Date;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
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
    }
}