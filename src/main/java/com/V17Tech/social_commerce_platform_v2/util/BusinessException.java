package com.V17Tech.social_commerce_platform_v2.util;

public class BusinessException extends RuntimeException {
    private Long code;
    private static final long serialVersionUID = -609099771903107222L;

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public BusinessException(String arg0) {
        super(arg0);
    }

    public BusinessException(String arg0, Long code) {
        super(arg0);
        this.code = code;
    }
}