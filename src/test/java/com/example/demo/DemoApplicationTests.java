package com.example.demo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigInteger;
import java.util.Random;

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


    @Test
    void test(){
        //指定位数
        Integer len = Integer.parseInt("6");
        //自增
        BigInteger num = new BigInteger("10");
        num = num.add(BigInteger.ONE);
        System.out.println(num);
        BigInteger max = BigInteger.TEN.pow(len).subtract(BigInteger.ONE);
        System.out.println(max);
        if (num.compareTo(max)<=0){
            //根据位数，补0
            String s  = String.format("%0"+len+"d",num);
            System.out.println("s----------"+s);
            String s1 = "qwer-";
            String str = s1.concat(s);
            System.out.println(str);
        }

        //随机数
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<len;i++){
            stringBuilder.append(random.nextInt(10));
        }
        String s1 = stringBuilder.toString();
        System.out.println("s1---------"+s1);
    }

    @Test
    public void test1(){
        String str1 = "HelloWorld";
        String str2 = "";
        System.out.println(str1.length());//获取字符串的长度
        System.out.println(str1.charAt(0));//获取字符串指定索引处的字符
        System.out.println(str1.isEmpty());//判断字符穿是否为空
        System.out.println(str2.isEmpty());

        str2 =str1.toUpperCase();//将字符串所有小写变为大写
        System.out.println(str1);//不会去改变str1的值，仍然为原来的字符串，只是返回一个将str1字符全部变为大写后的字符串。
        System.out.println(str2);

        str2 = str2.toLowerCase();//将字符串所有大写变为小写
        System.out.println(str2);

        System.out.println(str1.equals(str2));//比较字符串的内容是否相同
        System.out.println(str1.equalsIgnoreCase(str2));//与equals相同但是忽略大小写
    }

    @Test
    public void test2(){
        String str1 = "   he llo world   ";
        System.out.println("---"+str1+"---");
        System.out.println("---"+str1.trim()+"---");//去除字符串两边的空格

        str1 = str1.concat("abc");//将指定字符串链接到此字符串的结尾，等价于用“+”
        System.out.println(str1);

        String str2 = "abc";
        String str3 = "aAb";
        System.out.println(str2.compareTo(str3));//比较两个字符串的大小
        System.out.println(str2.compareToIgnoreCase(str3));//与compareTo相同但是忽略大小写
        //涉及到字符串排序
        //该方法用于两个相同数据类型的比较，两个不同类型的数据不能用此方法来比较。

        String str4 = "锄禾日当午";
        System.out.println(str4.substring(2));//返回一个新的字符串，它是字符串从beginIndex开始截取到最后的一个子字符串。
        System.out.println(str4.substring(1,3));//返回一个新的字符串，它是字符串从beginIndex开始截取到endIndex(不包含)的子字符串。
        System.out.println(str4);

    }

}
