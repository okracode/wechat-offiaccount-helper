package ren.ashin.wechat.intfc.util;

import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Mutable;

@Sources({"file:conf/config.properties"})
public interface MainConfig extends Mutable {

    @Key("jettyMode")
    String jettyMode();
    
    @Key("jettyPort")
    int jettyPort();
}
