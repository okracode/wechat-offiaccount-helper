package com.okracode.wx.subscription.web.util;

import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

@Sources({"file:conf/config.properties"})
public interface MainConfig extends Mutable {

    @Key("jettyMode")
    String jettyMode();
    
    @Key("jettyPort")
    int jettyPort();
    
    @Key("tulingRobot")
    String tulingRobot();
}
