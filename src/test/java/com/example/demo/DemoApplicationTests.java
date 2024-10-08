package com.example.demo;

import cn.hutool.json.JSONObject;
import cn.hutool.json.XML;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Test
    void test3(){
//        String string = "2023-10-31 00:99:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = sdf.parse(string);
//            System.out.println(date);
//            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-01 08:30:20");
//            System.out.println(date1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String date = "2022-03-13-2023-10-15";
//        String date1 = date.substring(0,10);
//        System.out.println(date1);
//        String date2 = date.substring(11,date.length());
//        System.out.println(date2);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//        try {
//            Date date3 = sdf.parse(date1);
//            System.out.println(date3);
//            Date date4 = sdf.parse(date2);
//            System.out.println(date4);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        /*
        * 方法一：根据年月获取月份对应第一天和最后一天
        * */
        String input = "2023-10";
        YearMonth yearMonth = YearMonth.parse(input);
        // 获取当月的第一天
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        System.out.println("当月的第一天：" + firstDayOfMonth);
        // 获取当月的最后一天
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        System.out.println("当月的最后一天：" + lastDayOfMonth);
        //localDate 转 Date
        Date date = Date.from(lastDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println("date：" + date);

        /*
         * 方法二：根据年月获取月份对应第一天和最后一天
         * */
//        String input = "2023-10";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//        try {
//            Date date = sdf.parse(input);
//
//            // 获取当月第一天
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.DAY_OF_MONTH, 1);
//            Date firstDayOfMonth = calendar.getTime();
//            System.out.println("当月的第一天：" + firstDayOfMonth);
//
//            // 获取当月最后一天
//            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//            Date lastDayOfMonth = calendar.getTime();
//            System.out.println("当月的最后一天：" + lastDayOfMonth);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @Test
    void test4(){

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(3);

        List<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(4);

        //获取并集
        Collection<Integer> unionList = CollectionUtils.union(list, list2);
        System.out.println(unionList);

        //获取交集
        Collection<Integer> intersectionList = CollectionUtils.intersection(list, list2);
        System.out.println(intersectionList);

        //获取交集的补集
        Collection<Integer> disjunctionList = CollectionUtils.disjunction(list, list2);
        System.out.println(disjunctionList);

        //获取差集
        Collection<Integer> subtractList = CollectionUtils.subtract(list, list2);
        System.out.println(subtractList);
    }

    @Test
    void test5(){
//        Integer integer1 = new Integer(128);
//
//        Objects.requireNonNull(integer1);
//        Objects.requireNonNull(integer1, "参数不能为空");
//        Objects.requireNonNull(integer1, () -> "参数不能为空");

        List<String> list = null;
        Assert.isTrue(CollectionUtils.isNotEmpty(list), "list不能为空");
        Assert.isTrue(CollectionUtils.isNotEmpty(list), () -> "list不能为空");
    }

    @Test
    void test6(){
        String data = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";

        String[] array = data.split(",");

        String lastFive1 = Arrays.stream(array)
                .skip(array.length - 5) // 跳过前面所有元素，保留最后5个
                .collect(Collectors.joining(","));
        System.out.println(lastFive1);
    }

    @Test
    void test7() throws ParseException {
        List<String> retList = new ArrayList<>();
        //todo 前端传过来的开始时间
        Date start = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2024-07-04 09:27");
        //todo 前端传过来的结束时间
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("2024-07-04 10:07");
        //todo 获取时间戳 start1 开始   end1 结束  end2 结束前一分钟
        Date start1 = new Date(start.getTime());
        Date end1 = new Date(end.getTime());

//        Date end2 = new Date(end.getTime() - 1 * 6000L);
//        System.out.println(start1);
//        System.out.println(end1);
//        System.out.println(end2);

        //todo 把获取的时间戳转成yyyyMMddHHmm的string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
//        String start2 = dateFormat.format(start1);
//        System.out.println(start2);
//
//        String end3 = dateFormat.format(end1);
//        System.out.println(end3);

        //todo  问题 我怎么利用开始和结束时间 循环这个期间内每分钟的时间，最终转换成 yyyyMMddHHmm的string
        //todo  只在开始和结束的这个时间范围内处理
        long diffIn = Math.abs(start.getTime() - end.getTime());
        System.out.println(diffIn);
        long diffInMinutes = (diffIn / (1000 * 60)) + 1; // 加1是因为需要包括开始时间
        System.out.println(diffInMinutes);
        long diffInMinutes1 = (diffIn / 60000L) + 1; // 加1是因为需要包括开始时间
        System.out.println(diffInMinutes1);
        for (int i = 0; i < diffInMinutes; i++) {
            Date currentMinute = new Date(start.getTime() + (i * 60000));
            String formattedDate = dateFormat.format(currentMinute);
            System.out.println(formattedDate);
        }

        // todo 之前是直接取当前时间，然后往前推40分钟，取每分钟换成 yyyyMMddHHmm的string，add到list里面，
        //  去数据库查 以 list 里面的40个 String的值为条件，现在是自己选时间段
//        int period = 40;
//        Date now = new Date();
//        for (int i = period -1; i >= 0; i--) {
//            Date past = new Date(now.getTime() - i * 6000L);
//            String pastStr = dateFormat.format(past);
//            retList.add(pastStr);
//        }

    }

    @Test
    void  test8() throws ParseException {
        LocalDateTime localDateTime = LocalDateTime.now();
//        String dateString = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        int batchNum = localDateTime.getHour();
//        for (int i = 1;i<=batchNum;i++){
//            String batchId = String.format("B%s%02d00",dateString,i);
//            System.out.println(batchId);
//        }
        String yesterday = localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String hour = localDateTime.format(DateTimeFormatter.ofPattern("HH"));
        System.out.println("yesterday------------"+yesterday);
        System.out.println("hour---------------"+hour);
    }

}
