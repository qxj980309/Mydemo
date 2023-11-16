package com.example.demo.mock.common.util;

import org.slf4j.MDC;

import java.util.UUID;

public class MDCUtil {
    public static void setSleuthTraceId(){
        MDC.put("X-B3-TraceId",generateTrace());
    }

    public static void setSleuthSpanId(){
        MDC.put("X-B3-SpanId",generateTrace());
    }

    private static String generateTrace() {
        return UUID.randomUUID().toString().replace("-","").substring(0,16);
    }
}
