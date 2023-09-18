package com.example.demo;

import cn.hutool.core.io.BufferUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.socket.nio.NioClient;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

public class CiMessageUtilSendTest {

    public static void main(String[] args) {
//        try{
            NioClient client = new NioClient("127.0.0.1",9999); //客户端
            client.setChannelHandler(socketChannel -> {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if(readBytes >0 ){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = StrUtil.utf8Str(bytes);
                    System.out.println(socketChannel.getLocalAddress()+"********"+body);
                } else if (readBytes <0){
                    socketChannel.close();
                }
            });
            client.listen();
//            System.out.println("输入发送消息");
//            Scanner scanner = new Scanner(System.in);
//            while(true){
//                String request = scanner.nextLine();
//                if (request != null && request.trim().length() > 0 ){
//                    byte[] data = StrUtil.bytes(request, Charset.forName("GBK"));
//                    byte[] bytes = { 0x30,0x30,0x30,0x30,0x30,0x30,0x01,0x45,0x43,0x48,0x4f,0x44,0x45,0x02,0x30,0x03};
//                    ByteBuffer wrap = ByteBuffer.wrap(bytes);
//                    client.write(wrap);
        Console.log("请输入发送的消息：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            if (request != null && request.trim().length() > 0) {
//                client.write(BufferUtil.createUtf8(request));
                byte[] bytes = { 0x30,0x30,0x30,0x39,0x37,0x30,0x35,0x30,0x30,0x30,0x30,0x30,
                        0x01,0x45,0x43,0x48,0x4f,0x5f,0x46,0x4c,0x41,0x47,0x02,0x30,0x03,0x50,0x43,0x4f,0x44,0x45,0x02,0x31,0x03,0x32,0x33,0x31,0x38,0x30,0x32};
//                        0x01,0x45,0x43,0x48,0x4f,0x5f,0x46,0x4c,0x41,0x47,0x02,0x30,0x03,0x31,
//                        0x01,0x50,0x43,0x4f,0x44,0x45,0x02,0x30,0x03,0x30,0x32,0x30,0x37,0x30,0x31,
//                        0x01,0x54,0x58,0x5f,0x43,0x4f,0x44,0x45,0x02,0x30,0x03,0x38,0x31,0x33,0x30,0x37,0x31,0x02,0x31,0x03,0x38,0x31,0x33,0x30,0x37,0x31,0x02,0x32,0x03,0x38,0x31,0x33,0x30,0x37,0x31};
                ByteBuffer wrap = ByteBuffer.wrap(bytes);
                client.write(wrap);
            }
        }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void test1() throws UnsupportedEncodingException{
        byte[] bytes = new byte[2];
        bytes[0] = 30;
        String gbk = new String(bytes,"GBK");
        System.out.println(gbk);

        byte[] header = StrUtil.bytes("0",Charset.forName("GBK"));
        System.out.println(header[0]);
    }
}
