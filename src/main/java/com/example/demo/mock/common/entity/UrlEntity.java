package com.example.demo.mock.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlEntity {
    private String sendSysCode;//发送方系统号
    private String receiveSysCode;// 接收方系统号
    private String url;
}
