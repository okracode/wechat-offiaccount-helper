package com.okracode.wx.subscription.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import org.junit.Test;

/**
 * @author Eric Ren
 * @date 2021/8/24
 */
public class JsonUtilTest {

    @Test
    public void testToJson() {
        Person person = new Person();
        person.setName("test");
        person.setAge(12);
        assertEquals("{\"n\":\"test\",\"a\":12}", JsonUtil.toJson(person));
    }

    @Test
    public void testFromJson() {
        String personStr = "{\"n\":\"test111\",\"a\":12}";
        assertEquals("test111", JsonUtil.fromJson(personStr, Person.class).getName());
    }

    @Test
    public void testConvert2Map() {
        String jsonMapStr = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        Map<String, Object> jsonMap = JsonUtil.convert2Map(jsonMapStr);
        assertEquals(2, jsonMap.size());
        assertTrue(jsonMap.containsKey("k1"));
        assertTrue(jsonMap.containsKey("k2"));
    }

    @Test
    public void testGetNode() {
        String jsonMapStr = "{\"k1\":\"v1\",\"k2\":\"v2\"}";
        JsonNode jsonNode = JsonUtil.getNode(jsonMapStr);
        assertEquals(2, jsonNode.size());
        assertEquals("v1", jsonNode.get("k1").asText());
        assertEquals("v2", jsonNode.get("k2").asText());
    }
}

class Person {

    @JsonProperty("n")
    private String name;
    @JsonProperty("a")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}