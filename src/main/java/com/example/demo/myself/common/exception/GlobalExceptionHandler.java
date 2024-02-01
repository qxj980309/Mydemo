package com.example.demo.myself.common.exception;

import com.example.demo.myself.common.result.R;
import com.example.demo.myself.common.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 统一异常处理
* ControllerAdvice注解的含义是当异常抛到controller层时会拦截下来
* */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 打印日志
     * 如果项目有集成lombok可使用@Slf4j注解代替
     */
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 使用ExceptionHandler注解声明处理Exception异常
     *
     * @param e e
     * @return {@link R}
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R exception(Exception e) {
        // 控制台打印异常
//        e.printStackTrace();
        // 控制台打印异常  借助工具类将错误堆栈输出到文件
        log.error(ExceptionUtils.getMessage(e));
        // 返回错误格式信息
        return R.error();
    }

    /**
     * 使用ExceptionHandler注解声明处理TestException异常
     *
     * @param e e
     * @return {@link R}
     */
    @ResponseBody
    @ExceptionHandler(TestException.class)
    public R exception(TestException e) {
        // 控制台打印异常
//        e.printStackTrace();
        // 控制台打印异常  借助工具类将错误堆栈输出到文件
        log.error(ExceptionUtils.getMessage(e));
        // 返回错误格式信息
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
