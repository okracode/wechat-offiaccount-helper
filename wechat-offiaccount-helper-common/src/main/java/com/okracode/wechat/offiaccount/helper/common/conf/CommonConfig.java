package com.okracode.wechat.offiaccount.helper.common.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommonConfig {

    @Value("${tulingRobot}")
    private String tulingRobot;

}
