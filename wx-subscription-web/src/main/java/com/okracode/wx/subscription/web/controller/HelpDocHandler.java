package com.okracode.wx.subscription.web.controller;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageHandler;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.exception.WxErrorException;
import java.util.Map;

/**
 * Handler代码采用单例模式，为了解决消息重试的问题。但由于网络问题导致返回结果慢了。会自动重试，返回多条信息。当然，这种做法不建议你们使用，因为这样写，同一时间仅仅允许一个线程进来处理，并不适合多线程环境。
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/17
 */
@Deprecated
public class HelpDocHandler implements WxMessageHandler {

    private static HelpDocHandler instance = null;

    private boolean isRun = false;

    private HelpDocHandler() {
    }

    public static synchronized HelpDocHandler getInstance() {
        if (instance == null) {
            instance = new HelpDocHandler();
        }
        return instance;
    }

    private synchronized boolean getIsRun() {
        return isRun;
    }

    private synchronized void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context, IService iService)
            throws WxErrorException {
        WxXmlOutMessage response = null;
        if (!getIsRun()) {
            setRun(true);
            response = execute(wxMessage);
            setRun(false);
        }
        return response;
    }

    private WxXmlOutMessage execute(WxXmlMessage wxMessage) {
        return WxXmlOutMessage.TEXT().content(ResponseConst.HELP).toUser(wxMessage.getFromUserName())
                .fromUser(wxMessage.getToUserName()).build();
    }
}
