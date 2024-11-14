package com.V17Tech.social_commerce_platform_v2.annotation;

import com.V17Tech.social_commerce_platform_v2.constant.RoleEnum;
import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionAspect {

    private Logger logger = LoggerFactory.getLogger(PermissionAspect.class);
    private final AccountService accountService;

    @Around("execution(* *(..)) && @annotation(permission)")
    public Object invoke(final ProceedingJoinPoint pjp, Permission permission) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(permission.target());
        if (token == null) return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);

        token = token.replaceAll("Bearer ", "");

        String userName = CommonUtil.getUserNameFromToken(token);
        boolean rlt = hasPermission(
                userName,
                permission.role()
        );
        if (rlt)
            return pjp.proceed();
        else return  ResponseEntity.status(HttpStatus.OK).body(permission.message());
    }

    public boolean hasPermission(String userName, String role) {
        boolean checkRole = false;
        AccountEntity account = accountService.getFirstByUsername(userName);
        logger.info(account.toString());
        if( account != null && account.getRole().getName().equals(role)){
            checkRole = true;
        }
        return checkRole;
    }
}
