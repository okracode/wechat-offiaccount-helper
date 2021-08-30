package com.okracode.wx.subscription.web;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.okracode.wx.subscription.service.util.ParseJson;
import com.okracode.wx.subscription.web.controller.CoreControllerTest;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {CoreControllerTest.class})
class WxSubscriptionServiceWebTests {

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
        try {
            // 读取城市编码文件
            ParseJson.parseJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterClass() throws ManagedProcessException {
        db.stop();
    }

}
