package com.okracode.wechat.offiaccount.helper.common;

import com.okracode.wechat.offiaccount.helper.common.conf.CommonConfigTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@RunWith(Suite.class)
@SuiteClasses(value = {CommonConfigTest.class, JsonUtilTest.class})
@SpringBootApplication
@ComponentScan("com.okracode.wechat.offiaccount.helper")
class WxSubscriptionCommonApplicationTests {

}
