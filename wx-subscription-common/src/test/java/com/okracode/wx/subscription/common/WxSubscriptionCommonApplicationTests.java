package com.okracode.wx.subscription.common;

import com.okracode.wx.subscription.common.conf.CommonConfigTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@RunWith(Suite.class)
@SuiteClasses(value = {CommonConfigTest.class, JsonUtilTest.class})
@SpringBootApplication
@ComponentScan("com.okracode.wx.subscription")
class WxSubscriptionCommonApplicationTests {

}
