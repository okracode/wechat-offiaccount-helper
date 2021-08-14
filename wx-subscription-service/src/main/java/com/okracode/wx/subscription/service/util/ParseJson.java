package com.okracode.wx.subscription.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
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
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            JSONArray cityObj = jsonObj.getJSONArray("城市代码");
            for (Object obj : cityObj) {
                JSONObject jObj = (JSONObject) obj;
                String privinceObj = jObj.getString("省");
                JSONArray cObjs = jObj.getJSONArray("市");
                for (Object cObj : cObjs) {
                    JSONObject cObj1 = (JSONObject) cObj;
                    String cityName = cObj1.getString("市名");
                    String cityCode = cObj1.getString("编码");
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
