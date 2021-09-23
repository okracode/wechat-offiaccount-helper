package com.okracode.wechat.offiaccount.helper.common.conf;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

/**
 * @author Eric Ren
 * @date 2021/8/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CommonConfigTest {

    @Resource
    private CommonConfig commonConfig;

    @Test
    public void testGetTulingRobot() throws IOException {
        InputStream in = new BufferedInputStream(
                new FileInputStream(ResourceUtils.getFile("classpath:application.properties")));
        Properties p = new Properties();
        p.load(in);
        assertEquals(p.getProperty("tulingRobot"), commonConfig.getTulingRobot());
    }
}