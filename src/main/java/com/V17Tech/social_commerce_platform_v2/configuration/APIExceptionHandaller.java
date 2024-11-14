package com.V17Tech.social_commerce_platform_v2.configuration;


import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@ResponseBody
public class APIExceptionHandaller {
    Logger logger = LoggerFactory.getLogger(APIExceptionHandaller.class);
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        logger.error("Error from :" + request.getContextPath() + " --Content: " + ex.getMessage());
       return ResponseEntity.status(HttpStatus.OK).body("Có lỗi xảy ra, vui lòng thử lại");
    }

    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request){
        logger.error("Error from :" + request.getContextPath() + " --Content: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body("Có lỗi xảy ra, vui lòng thử lại");
    }
}
