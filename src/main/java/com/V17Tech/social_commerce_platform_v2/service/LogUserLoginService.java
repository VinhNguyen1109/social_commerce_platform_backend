package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.LogUserLogin;

import java.util.List;

public interface LogUserLoginService {
   void saveLogLogin(LogUserLogin login);
   List<LogUserLogin> getByUserNameQuery(String name);
}
