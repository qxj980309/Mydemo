package com.example.demo.common;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.example.demo.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class CiMessageUtils {
    public CiMessageUtils(){
    }

    private final static Logger log = LoggerFactory.getLogger(CiMessageUtils.class);

    private static final String encoding = "GBK";

    /*
    *
    *解析ci 报文
    * @param data 输入报文
    */
    public static Map<String,Object> unpack(byte[] data){
        if (data == null){
            log.debug("报文为空");
            throw  new NullPointerException("报文为空");
        }
        log.info("解包开始");
        Map<String , Object> unpackResult = new HashMap<>();
        ByteArray unpackData =new ByteArray(data , 0);
        try{
            //识别初始序列
            unpackResult.put("Front_Data_Of_Start_Byte",getFrontDataOfStartByte(unpackData));
            //参数名
            String dataDict = null;
            //下标，用来区分list
            String index = null;
            while (!unpackData.isReadToEnd()){
                byte oneByte = unpackData.read(1)[0];
                //1:属性名开始标志 2;下标开始标志 3：参数值开始标志
                if (oneByte == 1){
                    dataDict = unpackDataDictFiled(unpackData);
                } else if (oneByte == 2){
                    index = unpackIndexFiled(unpackData);
                } else if (oneByte == 3){
                    Object valueOfDict = unpackValueOfDictFiled(unpackData);
                    storeDataCell(dataDict,index,valueOfDict,unpackResult);
                }
            }
        }catch (Exception var3){
            log.error("解包ci后台报文出错");
            throw new BizException("解包ci后台报文出错");
        }
        log.info("解包结束");
        return unpackResult;
    }

    public static byte[] pack(Map<String, Object> obj){
        log.debug("ci报文打包开始");
        ByteArrayOutputStream packData = new ByteArrayOutputStream();
        if(obj == null){
            log.error("报文为空");
            throw new BizException("打包数据为空");
        }

        try {
            //识别初始序列，对象中保存的Front_Data_Of_Start_Byte值
            packFrontDataOfStartByte(packData,obj);
            //原始方法解析数据池和DataObject两种方式，这里采取DataObject方式（类似Mao格式）
            packFrontDataObject(packData,obj);
        }catch (Exception e){
            log.error("报文为空");
            throw new BizException("打包ci后台报文出错");
        }
        log.debug("ci报文打包结束");
        return packData.toByteArray();
    }

    private static String getFrontDataOfStartByte(ByteArray unpackData) throws UnsupportedEncodingException {
        byte[] frontDataOfStartByte = unpackFrontDataOfStartByte(unpackData);
        String steData = new String(frontDataOfStartByte,encoding);
        log.debug("开始字符之前数据（Front_Data_Of_Start_Byte）："+ steData);
        return steData;
    }

    private static byte[] unpackFrontDataOfStartByte(ByteArray unpackData) {
        ByteArrayOutputStream dataDictBytesStream = new ByteArrayOutputStream();

        while (!unpackData.isReadToEnd()){
            byte[] read = unpackData.read(1);
            byte oneByte = read[0];
            if (oneByte == 1){
                int currentOffset = unpackData.getOffset();
                unpackData.setOffset(currentOffset-1);
                break;
            }

            if (oneByte == 2){
                log.error("字节流开始应该为0x01，而不是0x02");
                throw new BizException("字节流开始应该为0x01，而不是0x02");
            }

            if (oneByte == 3){
                log.error("字节流开始应该为0x01，而不是0x03");
                throw new BizException("字节流开始应该为0x01，而不是0x03");
            }

            if(oneByte == 92){
                handleEscapeChar(unpackData,dataDictBytesStream);
            } else {
                dataDictBytesStream.write(oneByte);
            }
        }
        return dataDictBytesStream.toByteArray();
    }

    private static void handleEscapeChar(ByteArray unpackData, ByteArrayOutputStream bytes) {
        byte oneByte = unpackData.read(1)[0];
        if(oneByte!=1 && oneByte!=2 && oneByte!=3){
            bytes.write(oneByte);
        } else {
            bytes.write(oneByte);
        }
    }

    /*
    * 解析属性名
    * */
    private static String unpackDataDictFiled(ByteArray unpackData) throws UnsupportedEncodingException{
        byte[] dictBytes = getDateDictBytes(unpackData);
        String dataDict = new String(dictBytes,encoding);
        log.debug("解包数据字典名称: "+dataDict);
        return dataDict;
    }

    private static byte[] getDateDictBytes(ByteArray unpackData) {
        ByteArrayOutputStream dataDictBytesStream = new ByteArrayOutputStream();

        while (!unpackData.isReadToEnd()){
            byte oneByte = unpackData.read(1)[0];
            if (oneByte == 1){
                log.error("字节流开始字符0x01后,应先出现中间字符0x02，而不是开始字符0x01");
                throw new BizException("字字节流开始字符0x01后,应先出现中间字符0x02，而不是开始字符0x01");
            }

            if (oneByte == 2){
                int currentOffset = unpackData.getOffset();
                unpackData.setOffset(currentOffset-1);
                break;
            }

            if (oneByte == 3){
                log.error("字节流开始字符0x01后,应先出现中间字符0x02，而不是开始字符0x03");
                throw new BizException("字字节流开始字符0x01后,应先出现中间字符0x02，而不是开始字符0x03");
            }

            if(oneByte == 92){
                handleEscapeChar(unpackData,dataDictBytesStream);
            } else {
                dataDictBytesStream.write(oneByte);
            }
        }

        return dataDictBytesStream.toByteArray();
    }

    /*
    * 解析下标
    * */
    private static String unpackIndexFiled(ByteArray unpackData) throws UnsupportedEncodingException{
        byte[] indexBytes = getIndexBytes(unpackData);
        String index = new String(indexBytes,encoding);
        log.debug("解包数据字典下标: " + index );
        return index;
    }

    private static byte[] getIndexBytes(ByteArray unpackData) {
        ByteArrayOutputStream indexBytesStream = new ByteArrayOutputStream();
        while (true){
            if (!unpackData.isReadToEnd()){
                byte oneByte = unpackData.read(1)[0];
                if (oneByte == 1){
                    log.error("中间字符0x02后，应该出现结束字符0x03，而不是0x01");
                    throw new BizException("中间字符0x02后，应该出现结束字符0x03，而不是0x01");
                }

                if (oneByte == 2){
                    log.error("中间字符0x02后，应该出现结束字符0x03，而不是0x02");
                    throw new BizException("中间字符0x02后，应该出现结束字符0x03，而不是0x02");
                }

                if (oneByte != 3){
                    if(oneByte == 92){
                        handleEscapeChar(unpackData,indexBytesStream);
                    } else {
                        indexBytesStream.write(oneByte);
                    }
                    continue;
                }
                int currentOffset = unpackData.getOffset();
                unpackData.setOffset(currentOffset -1);
            }
            return indexBytesStream.toByteArray();
        }
    }

    /*
    * 解析参数值
    * */
    private static Object unpackValueOfDictFiled(ByteArray unpackData) throws UnsupportedEncodingException{
        byte[] valueOfDictBytes = getValueOfDictBytes(unpackData);
        Object valueOfDict = parseDataType(valueOfDictBytes);
        log.debug("解包数据字典值：" ,valueOfDict);
        return valueOfDict;
    }

    private static byte[] getValueOfDictBytes(ByteArray unpackData) {
        ByteArrayOutputStream valueOfDictBytesStream = new ByteArrayOutputStream();

        while (!unpackData.isReadToEnd()){
            byte oneByte = unpackData.read(1)[0];
            int currentOffset;
            if (oneByte == 1){
                currentOffset = unpackData.getOffset();
                unpackData.setOffset(currentOffset -1);
                break;
            }

            if (oneByte == 2){
                currentOffset = unpackData.getOffset();
                unpackData.setOffset(currentOffset -1);
                break;
            }

            if (oneByte == 3){
                log.error("结束字符0x03，应该先出现0x01，而不是结束字符0x03");
                throw new BizException("结束字符0x03，应该先出现0x01，而不是结束字符0x03");
            }

            if(oneByte == 92){
                handleEscapeChar(unpackData,valueOfDictBytesStream);
            } else {
                valueOfDictBytesStream.write(oneByte);
            }
        }

        return valueOfDictBytesStream.toByteArray();

    }

    private static Object parseDataType(byte[] valueOfDictBytes) throws UnsupportedEncodingException{
        /*
        *获取数据类型；判断是否属于MoneyDataType；判断是否数据BinaryDataType；其他情况直接转为String
        **/
        String valueOfDict = new String(valueOfDictBytes,encoding);
        return valueOfDict;
    }

    private static void storeDataCell(String dataDict, String index, Object valueOfDict , Map<String, Object> unpackResult) {
        if (isRepeatDict(index)){
            handleRepeatDict(dataDict, valueOfDict, unpackResult);
        } else {
            unpackResult.put(dataDict, valueOfDict);
            log.debug("解包数据字典值（存入unpackResult）"+valueOfDict);
        }
    }

    private static boolean isRepeatDict(String index) {
        return !"0".equals(index);
    }

    private static void handleRepeatDict(String dataDict, Object valueOfDict , Map<String, Object> unpackResult) {
        Object obj = unpackResult.get(dataDict);
        if (obj instanceof List){
            ((List) obj).add(valueOfDict);
        } else {
            List<Object> list = new ArrayList<>();
            list.add(obj);
            list.add(valueOfDict);
            unpackResult.put(dataDict,list);
        }
    }

    private static void packFrontDataOfStartByte(ByteArrayOutputStream packData,Map<String, Object> packObject) throws IOException {
        Object frontDataOfStartByte = packObject.get("Front_Data_Of_Start_Byte");
        String strData = String.valueOf(frontDataOfStartByte);
        if (null != frontDataOfStartByte){
            packData.write(strData.getBytes(encoding));
            log.debug("打包开始字符之前数据: "+ strData);
        } else {
            log.debug("未找到开始字符之前数据");
        }

    }

    private static void packFrontDataObject(ByteArrayOutputStream packData,Map<String, Object> packObject) throws IOException {
        /*
         * 解包流程
         * 1。原始方法考虑特殊类型，这里省略，主要考虑单个值和List类型
         */
//        Map<String,Object> packObject = (Map<String, Object>) obj;
        for(String key : packObject.keySet()){
            Object value = packObject.get(key);
            if (value instanceof  Map){
                packFrontDataObject(packData,(Map<String, Object>) value);
            } else {
                packDataEntry(value , key ,packData);
            }

        }

    }

    private static void packDataEntry(Object valueOfDict , String dataDict ,ByteArrayOutputStream packData ) throws IOException {
        if (valueOfDict instanceof List){
            List valueList =(List) valueOfDict;
            if (packDataDict(dataDict,packData)){
                for (int i = 0; i <valueList.size();++i){
                    packIndex(i,packData);
                    packValveOfDict(valueList.get(i),packData);
                }
            }
        } else if (packDataDict(dataDict ,packData)){
            packIndex(0,packData);
            packValveOfDict(valueOfDict,packData);
        }

    }

    private static boolean packDataDict(String dataDict, ByteArrayOutputStream packData) throws IOException {
        //判断必输是否数据数据字典的属性名
        packData.write(1);
        packData.write(dataDict.getBytes(encoding));
        log.warn("打包数据字典："+ dataDict);
        return true;
    }

    private boolean havingDataDict(String dataDict) {
        return true;
    }

    private static void packIndex(int index,ByteArrayOutputStream packData) throws IOException {
        packData.write(2);
        packData.write(String.valueOf(index).getBytes(encoding));
        log.debug("打包下标: ",index);
    }

    private static void packValveOfDict(Object valueOfDict , ByteArrayOutputStream packData) throws IOException {
        packData.write(3);
        byte[] dictValue = getFormatValueOfDict(valueOfDict);
        dictValue = transferValveOfDict(dictValue);
        packData.write(dictValue);
        log.debug("打包数据字典值: " + valueOfDict);
    }

    private static byte[] transferValveOfDict(byte[] dictValue) {
        ByteArrayOutputStream transferredDictValve = new ByteArrayOutputStream();
        for(byte oneChar : dictValue){
            if (oneChar ==1 || oneChar == 2 || oneChar == 3 || oneChar == 92) {
                transferredDictValve.write(92);
            }

            transferredDictValve.write(oneChar);
        }

        return transferredDictValve.toByteArray();
    }

    /*
    * 获取带格式的value
    */

    private static byte[] getFormatValueOfDict(Object valueOfDict) throws UnsupportedEncodingException{
        String valveOfDictTemp = (String) valueOfDict;
        return valveOfDictTemp.getBytes(encoding);
    }

    static class ByteArray{

        private byte[] bytes;

        private int offset;

        public ByteArray(){

        }

        public ByteArray(byte[] ba,int offset) {
            this.bytes = ba;
            this.offset = offset;
        }

        public byte[] getBytes() {
            return this.bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public void addOffset(int length){
            this.offset += length;
        }

        public byte[] read(int length){
            byte[] data = new byte[length];
            System.arraycopy(this.bytes,this.offset,data,0,length);
            this.offset += length;
            return data;
        }

        public boolean isReadToEnd(){
            return this.offset >= this.bytes.length;
        }
    }

}
