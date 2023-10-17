package com.example.demo.mock.common.util;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.example.demo.common.exception.BizException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class CiMessageUtil {
    public CiMessageUtil(){
    }

    private final static Logger log = LoggerFactory.getLogger(CiMessageUtil.class);


    private ByteArray unpackData;

    private String dataDict;

    private String index;

    private Object valueOfDict;

    private Map<String , Object> unpackResult;

    private String encoding = "GBK";
            //DataByteArrayOutputStream
    private DataByteArrayOutputStream packData;

    /*
    *
    *解析ci 报文
    *
    */
    public Map<String,Object> unpack(byte[] data){
        if (data == null){
            log.debug("报文为空");
            throw  new NullPointerException("报文为空");
        }
        log.info("解包开始");
        try{
            unpackData = new ByteArray(data , 0);
            unpackResult =new HashMap<>();
            //识别初始序列
            storeFrontDataOfStartByte();

            while (!unpackData.isReadToEnd()){
                byte oneByte = unpackData.read(1)[0];

                //1:属性名开始标志 2;下标开始标志 3：参数值开始标志
                if (oneByte == 1){
                    unpackDataDictFiled();
                } else if (oneByte == 2){
                    unpackDataIndexFiled(unpackData);
                } else if (oneByte == 3){
                    unpackValueOfDictFiled();
                }
            }
        }catch (Exception var3){
            log.error("解包ci后台报文出错");
            throw new BizException("解包ci后台报文出错");
        }
        log.info("解包结束");
        return unpackResult;
    }

    public byte[] pack(Object obj){
        log.debug("ci报文打包开始");
        packData = new DataByteArrayOutputStream();
        if(obj == null){
            log.error("报文为空");
            throw new NullPointerException("打包数据为空");
        }

        try {
            //识别初始序列，对象中保存的Front_Data_Of_Start_Byte值
            packFrontDataOfStartByte(obj);
            //原始方法解析数据池和DataObject两种方式，这里采取DataObject方式（类似Mao格式）
            packFrontDataObject(obj);
        }catch (Exception e){
            log.error("报文为空");
            throw new NullPointerException("打包ci后台报文出错");
        }
        log.info("ci报文打包结束");
        return packData.toByteArray();
    }

    private void storeFrontDataOfStartByte() throws UnsupportedEncodingException {
        byte[] frontDataOfStartByte = unpackFrontDataOfStartByte();
        String steData = new String(frontDataOfStartByte,encoding);
        unpackResult.put("Front_Data_Of_Start_Byte",steData);
        log.debug("开始字符之前数据存入数据池（Front_Data_Of_Start_Byte）"+ steData);
    }

    private byte[] unpackFrontDataOfStartByte() {
        DataByteArrayOutputStream dataByteArrayOutputStream = new DataByteArrayOutputStream();
        while (!unpackData.isReadToEnd()){
            byte oneByte = unpackData.read(1)[0];
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
                handleEscapeChar(unpackData,dataByteArrayOutputStream);
            } else {
              dataByteArrayOutputStream.write(oneByte);
            }
        }
        return dataByteArrayOutputStream.toByteArray();
    }

    private void handleEscapeChar(ByteArray unpackData, DataByteArrayOutputStream bytes) {
        byte oneByte = unpackData.read(1)[0];
        if (oneByte != 1 && oneByte != 2 &&  oneByte != 3){
            bytes.write(oneByte);
        } else {
            bytes.write(oneByte);
        }
    }

    /*
    * 解析属性名
    * */
    private void unpackDataDictFiled() throws UnsupportedEncodingException{
        byte[] dictBytes = getDateDictBytes();
        dataDict = new String(dictBytes,encoding);
        log.debug("解包数据字典名称"+dataDict);
    }

    private byte[] getDateDictBytes() {
        DataByteArrayOutputStream dataDictBytesStream = new DataByteArrayOutputStream();

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
    private void unpackDataIndexFiled(ByteArray unpackData) throws UnsupportedEncodingException{
        byte[] indexBytes = getIndexBytes(unpackData);
        index = new String(indexBytes,encoding);
        log.debug("解包数据字典下标"+ ByteArrayUtil.toHexString(indexBytes) );
    }

    private byte[] getIndexBytes(ByteArray unpackData) {
        DataByteArrayOutputStream indexBytesStream = new DataByteArrayOutputStream();
        while (true){
            if (unpackData.isReadToEnd()){
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
    private void unpackValueOfDictFiled() throws UnsupportedEncodingException{
        byte[] valueOfDictBytes = getValueOfDictBytes();
//        valueOfDict = new String(valueOfDictBytes,encoding);
        valueOfDict = parseDataType(valueOfDictBytes);
        storeDataCell(dataDict,valueOfDict);
    }

    private byte[] getValueOfDictBytes() {
        DataByteArrayOutputStream valueOfDictBytesStream = new DataByteArrayOutputStream();

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

    private Object parseDataType(byte[] valueOfDictBytes) throws UnsupportedEncodingException{
        /*
        *获取数据类型；判断是否属于MoneyDataType；判断是否数据BinaryDataType；其他情况直接转为String
        **/
        valueOfDict = new String(valueOfDictBytes,encoding);
        return valueOfDict;
    }

    private void storeDataCell(String dataDict, Object valueOfDict) {
        if (isRepeatDict()){
            handleRepeatDict();
        } else {
            unpackResult.put(dataDict,valueOfDict);
            log.debug("解包数据字典值（存入unpackResult）"+valueOfDict);
        }
    }

    private boolean isRepeatDict() {
        return !"0".equals(index);
    }

    private void handleRepeatDict() {
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

    private void packFrontDataOfStartByte(Object obj) throws IOException {
        Map<String,Object> packObject =(Map<String, Object>) obj;
        Object frontDataOfStartByte = packObject.get("Front_Data_Of_Start_Byte");
        String strData = String.valueOf(frontDataOfStartByte);
        if (null != frontDataOfStartByte){
            packData.write(strData.getBytes(encoding));
            log.debug("打包开始字符之前数据"+ strData);
        } else {
            log.debug("未找到开始字符之前数据");
        }

    }

    private void packFrontDataObject(Object obj) throws IOException {
        /*
         * 解包流程
         * 1。原始方法考虑特殊类型，这里省略，主要考虑单个值和List类型
         */
        Map<String,Object> packObject = (Map<String, Object>) obj;
        for(String key : packObject.keySet()){
            Object value = packObject.get(key);
            if (value instanceof  Map){
                packFrontDataObject(value);
            } else {
                valueOfDict = value;
                dataDict = key;
                packDataEntry();
            }

        }

    }

    private void packDataEntry() throws IOException {
        if (valueOfDict instanceof List){
            List valueOfDicts =(List) valueOfDict;
            if (packDataDict(dataDict)){
                for (int i = 0; i <valueOfDicts.size();i++){
                    packIndex(i);
                    packValveOfDict(dataDict,valueOfDicts.get(i));
                }
            }
        } else if (packDataDict(dataDict)){
            packIndex(0);
            packValveOfDict(dataDict,valueOfDict);
        }

    }

    private boolean packDataDict(String dataDict) throws IOException {
        //判断必输是否数据数据字典的属性名
//        if(!havingDataDict(dataDict)){
//            return false;
//        } else {
//
//        }
        packData.write(1);
        packData.write(dataDict.getBytes(encoding));
        log.warn("打包数据字典："+ dataDict);
        return true;
    }

    private boolean havingDataDict(String dataDict) {
        return true;
    }

    private void packIndex(int index) throws IOException {
        packData.write(2);
        packData.write(String.valueOf(index).getBytes(encoding));
        log.debug("打包下标");
    }

    private void packValveOfDict(String dataDict, Object valueOfDict) throws IOException {
        packData.write(3);
        byte[] dictValue = getFromatedValueOfDict(dataDict,valueOfDict);
        dictValue = transferValveOfDict(dictValue);
        packData.write(dictValue);
        log.debug("打包数据字典值" + valueOfDict);
    }

    private byte[] transferValveOfDict(byte[] dictValue) {
        DataByteArrayOutputStream transferredDictValve = new DataByteArrayOutputStream();
        byte[] dictValueByte = dictValue;
        int len = dictValue.length;

        for(int i = 0; i< len; i++){
            byte oneChar = dictValueByte[i];
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

    private byte[] getFromatedValueOfDict(String dataDict, Object valueOfDict) throws UnsupportedEncodingException{
        byte[] byteData;
        String valveOfDictTemp = (String) valueOfDict;
        byteData = valveOfDictTemp.getBytes(encoding);
        return null == byteData ? new byte[0] : byteData;
    }

    class ByteArray{

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


    class DataByteArrayOutputStream extends  ByteArrayOutputStream {

        private byte[] writeBuffer = new byte[0];

        public DataByteArrayOutputStream (int size){
            super(size);
        }

        public DataByteArrayOutputStream(){
            super(32);
        }


        public final void writeInt(int v) throws IOException{
            write(v >>> 24 & 255);
            write(v >>> 16 & 255);
            write(v >>> 8 & 255);
            write(v >>> 0 & 255);
        }

        public final void writeLong(long v) throws Exception{
            writeBuffer[0] = (byte)((int) (v >>> 56));
            writeBuffer[1] = (byte)((int) (v >>> 48));
            writeBuffer[2] = (byte)((int) (v >>> 40));
            writeBuffer[3] = (byte)((int) (v >>> 32));
            writeBuffer[4] = (byte)((int) (v >>> 24));
            writeBuffer[5] = (byte)((int) (v >>> 16));
            writeBuffer[6] = (byte)((int) (v >>> 8));
            writeBuffer[7] = (byte)((int) (v >>> 0));
            write(writeBuffer,0,8);
        }


    }

}
