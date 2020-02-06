package com.biao.mall.common.enums;

/**
 * @Classname RspStatusEnum
 * @Description TODO
 * @Author xiexiaobiao
 * @Date 2019-10-06 12:45
 * @Version 1.0
 **/
public enum  RspStatusEnum {

    SUCCESS(200,"成功"),
    FAIL(999,"失败"),
    EXCEPTION(500,"系统异常");

    private int code;
    private String message;

    RspStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
