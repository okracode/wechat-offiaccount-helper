package com.okracode.wx.subscription.service.util;

import java.io.IOException;
import org.apache.commons.collections4.MapUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Eric Ren
 * @date 2021/8/30
 */
public class ParseJsonTest {

    @Test
    public void testParseJsonFile() throws IOException {
        ParseJson.parseJsonFile();
        Assert.assertTrue(MapUtils.isNotEmpty(ParseJson.cityCodeMap));
    }
}