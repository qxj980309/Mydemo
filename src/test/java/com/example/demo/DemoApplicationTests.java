package com.example.demo;


import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.XMLFormatter;

@SpringBootTest
class DemoApplicationTests {

    //xml转JSON
    @Test
    void contextLoads() {
        String xml = "<class id= '1'><student><name>aaaaaa</name><age>21</age></student><student><name>bbbbbb</name><age>22</age></student></class>";
        //将xml转为json
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        //设置缩进
        String jsonPrettyPrintString = xmlJSONObj.toString();
        //根据key值获取JSONObject对象中对应的value值，获取到的值是Object类型，需要手动转化为需要的数据类型
        Object root = xmlJSONObj.get("class");
        //如果JSONObjct对象中的value是一个JSONObject对象，即根据key获取对应的JSONObject对象；
        Object root1 = xmlJSONObj.getJSONObject("class");
        //输出格式化后的json
        System.out.println(jsonPrettyPrintString);
        System.out.println(root);
        System.out.println(root1);
    }

}
