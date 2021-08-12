package com.okracode.wx.subscription.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MainConfig {

    @Value("${jettyMode}")
    private String jettyMode;

    @Value("${jettyPort}")
    private int jettyPort;

    @Value("${tulingRobot}")
    private String tulingRobot;

    public String getJettyMode() {
        return jettyMode;
    }

    public int getJettyPort() {
        return jettyPort;
    }

    public String getTulingRobot() {
        return tulingRobot;
    }
}
