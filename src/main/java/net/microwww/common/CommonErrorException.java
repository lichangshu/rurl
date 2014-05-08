/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.microwww.common;

/**
 * 支付方式产生的错误!
 *
 * @author changshu.li
 */
public class CommonErrorException extends Exception {

    public static final int USER_INFO_NAME_DOUBLE = 1;
    public static final int PHONE_DOUBLE = 2;
    public static final int NO_RIGHT_UPDATE_USER_INFO = 3;
    public static final int CUSTOM_ERROR = -99;
    private int code;

    public CommonErrorException(String message, int code) {
        super(message);
        this.setCode(code);
    }

    public CommonErrorException(String message, Exception ex) {
        super(message, ex);
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }
}
