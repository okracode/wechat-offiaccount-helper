package com.okracode.wx.subscription.web.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MainConfig {

    @Value("${tulingRobot}")
    private String tulingRobot;

    @Value("${token}")
    private String token;

}
