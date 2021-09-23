package com.okracode.wx.subscription.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Maps;
import com.okracode.wx.subscription.common.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author Eric Ren
 * @ClassName: ParseJson
 * @Description: 城市编码文件解析类
 * @date May 9, 2017
 */
@Slf4j
public class ParseJson {

    public static Map<String, String> cityCodeMap = Maps.newHashMapWithExpectedSize(500);

    public static void parseJsonFile() throws IOException {
        String userHome = System.getProperty("user.home");
        Resource pathResource = new ClassPathResource("cityCode.json");
        File jsonFile = FileUtils.getFile(userHome, "cityCode.json");
        FileUtils.copyInputStreamToFile(pathResource.getInputStream(), jsonFile);
        String jsonStr = null;
        if (jsonFile.exists()) {
            try {
                jsonStr = FileUtils.readFileToString(jsonFile, "UTF-8");
            } catch (IOException e) {
                log.warn("文件读取失败：" + "classpath:cityCode.json");
            }
        } else {
            log.warn("找不到城市编码文件：" + "classpath:cityCode.json");
        }
        // 开始解析文件
        if (jsonStr != null) {
            JsonNode jsonObj = JsonUtil.getNode(jsonStr);
            ArrayNode cityObj = (ArrayNode)jsonObj.findValue("城市代码");
            for (JsonNode jObj : cityObj) {
                String privinceObj = jObj.get("省").asText();
                ArrayNode cObjs = (ArrayNode)jObj.findValue("市");
                for (JsonNode cObj1 : cObjs) {
                    String cityName = cObj1.get("市名").asText();
                    String cityCode = cObj1.get("编码").asText();
                    if (cityCodeMap.containsKey(cityName)) {
                        log.warn("发现相同的城市名：" + cityName + "最终存储的城市为:" + privinceObj + "."
                                + cityName);
                    }
                    cityCodeMap.put(cityName, cityCode);
                }
            }
        }
    }
}
