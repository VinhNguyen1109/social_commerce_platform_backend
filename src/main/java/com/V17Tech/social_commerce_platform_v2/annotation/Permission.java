package com.V17Tech.social_commerce_platform_v2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Permission {
    String role() default "";
    String target() default "Authorization";
    String message() default "Bạn không có quyền thực hiện thao tác này";
}
