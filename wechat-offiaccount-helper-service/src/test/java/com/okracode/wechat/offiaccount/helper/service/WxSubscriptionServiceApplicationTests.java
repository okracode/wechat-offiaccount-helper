package com.okracode.wechat.offiaccount.helper.service;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.okracode.wechat.offiaccount.helper.service.chatbot.impl.QingyunkeApiServiceImplTest;
import com.okracode.wechat.offiaccount.helper.service.event.ApplicationEventListenerTest;
import com.okracode.wechat.offiaccount.helper.service.util.ParseJsonTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@RunWith(Suite.class)
@SuiteClasses(value = {ParseJsonTest.class, ApplicationEventListenerTest.class, CoreServiceTest.class,
        QingyunkeApiServiceImplTest.class})
@SpringBootApplication
@ComponentScan("com.okracode.wechat.offiaccount.helper")
@MapperScan("com.okracode.wechat.offiaccount.helper.repository.dao")
class WxSubscriptionServiceApplicationTests {

    private static final int DB_PORT = 3309;
    private static DB db;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBConfigurationBuilder builder = DBConfigurationBuilder.newBuilder();
        builder.setPort(DB_PORT);
        builder.addArg("--user=root");
        builder.addArg("--enable-lower_case_table_names");
        db = DB.newEmbeddedDB(builder.build());
        db.start();
        db.source("sql/init.sql");
    }

    @AfterClass
    public static void afterClass() throws ManagedProcessException {
        db.stop();
    }

}
