package com.okracode.wechat.offiaccount.helper.service.chatbot.impl;

import static org.junit.Assert.assertNotNull;

import com.okracode.wechat.offiaccount.helper.service.chatbot.ChatBotApiService;
import javax.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
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
public class QingyunkeApiServiceImplTest {

    @Resource
    private ChatBotApiService qingyunkeApiServiceImpl;

    @Test
    public void testCallOpenApi() {
        assertNotNull(qingyunkeApiServiceImpl.callOpenApi(RandomStringUtils.randomAlphabetic(10)));
    }
}