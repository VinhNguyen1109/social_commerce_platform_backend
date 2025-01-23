package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.model.LoginRequest;
import com.V17Tech.social_commerce_platform_v2.model.LoginUserDTO;

public interface AccountService {

    Long countNoAccount();

    AccountEntity getFirstByUsername(String username);

    void saveAccount(AccountEntity accountEntity);

     LoginUserDTO login (LoginRequest loginRequest) ;

     String resetPassword(String token, String newPassword);

     String logout(String token);
}
