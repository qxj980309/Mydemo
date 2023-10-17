package com.example.demo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.socket.nio.NioServer;
import com.example.demo.mock.common.util.CiMessageUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.Map;

public class CiMessageUtilTest {
    private final static Logger log = LoggerFactory.getLogger(CiMessageUtilTest.class);

    public static void main(String[] args) {
        try{
            NioServer server = new NioServer(9999); // 服务端
            String encodeType = "01";
            server.setChannelHandler(sc ->{
//                CiMessageUtil ciMessageUtil = new CiMessageUtil();
//                ByteBuffer bodyBuffer = ByteBuffer.allocate(2048);
//                sc.read(bodyBuffer);
//                byte[] bytes = new byte[bodyBuffer.remaining()];
//                bodyBuffer.get(bytes);
//                String body = StrUtil.str(bytes, Charset.forName("GBK"));
//                log.info("",body);
//                System.out.println("----------");
//                Console.log("[{}]: {}", sc.getRemoteAddress(), body);
//                System.out.println("==========");
//                Map<String,Object> unpack = ciMessageUtil.unpack(bytes);
//                System.out.println(unpack);
//                byte[] pack = ciMessageUtil.pack(unpack);
//                System.out.println(pack);
//                Map<String,Object> testPack = ciMessageUtil.unpack(pack);
//                System.out.println(testPack);
//                sc.write(BufferUtil.createUtf8(body));
                ByteBuffer bodyBuffer = ByteBuffer.allocate(2048);
                sc.read(bodyBuffer);
                bodyBuffer.flip();
                byte[] bytes = new byte[bodyBuffer.remaining()];
                bodyBuffer.get(bytes);
                String body = StrUtil.str(bytes,"GBK");
                log.info("接受数据为：{}",body);
                Map<String,Object> unpack = CiMessageUtils.unpack(bytes);
                System.out.println("************************************************");
                System.out.println(unpack);
                System.out.println("************************************************");

                byte[] pack = CiMessageUtils.pack(unpack);
                System.out.println("************************************************");
                System.out.println(pack);
                System.out.println("************************************************");

                Map<String,Object> testPack = CiMessageUtils.unpack(pack);
                System.out.println("************************************************");
                System.out.println(testPack);
                System.out.println("************************************************");

            });
            server.listen();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
