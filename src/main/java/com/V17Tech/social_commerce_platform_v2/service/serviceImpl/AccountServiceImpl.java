package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;


import com.V17Tech.social_commerce_platform_v2.configuration.KeyCloakProvider;
import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import lombok.RequiredArgsConstructor;
import com.V17Tech.social_commerce_platform_v2.model.LoginRequest;
import com.V17Tech.social_commerce_platform_v2.model.LoginUserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.V17Tech.social_commerce_platform_v2.repository.AccountRepository;
import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;
    private final KeyCloakProvider kcProvider;

    private final PasswordEncoder passwordEncoder;


    @Override
    public AccountEntity getFirstByUsername(String username) {
        return accountRepository.getFirstByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void saveAccount(AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
    }
    @Override
    public LoginUserDTO login(LoginRequest loginRequest)  {
                AccountEntity accountEntity = accountRepository.getFirstByUsername(loginRequest.getUsername());
                Keycloak keycloak = kcProvider
                        .newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword())
                        .build();
                AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();
                return LoginUserDTO.builder()
                        .accessToken(accessTokenResponse.getToken())
                        .tokenType(accessTokenResponse.getTokenType())
                        .refreshToken(accessTokenResponse.getRefreshToken())
                        .expiresIn(accessTokenResponse.getExpiresIn())
                        .username(loginRequest.getUsername())
                        .lastname(accountEntity.getLastname())
                        .firstname(accountEntity.getFirstname())
                        .build();
    }

    @Override
    public String resetPassword(String token, String newPassword) {
        String username =  CommonUtil.getUserNameFromToken(token);
        if(username == null){
            throw new BusinessException("Token không chính xác");
        }
        AccountEntity accountEntity = accountRepository.getFirstByUsername(username);
        if(accountEntity == null) throw new BusinessException("Không tồn tại account");
        UsersResource userResource = kcProvider.getKeycloakUserResource();
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(newPassword.trim());
        // Set password credential
        userResource.get(accountEntity.getKeycloakId()).resetPassword(passwordCred);
        logger.info("password after change: " + newPassword.trim());
        accountEntity.setPassword(passwordEncoder.encode(newPassword.trim()));
        accountRepository.save(accountEntity);
        return "reset password successfully";
    }

    @Override
    public String logout(String token) {
        String username =  CommonUtil.getUserNameFromToken(token);
        if(username == null){
            throw new BusinessException("Token không chính xác");
        }
        AccountEntity accountEntity = accountRepository.getFirstByUsername(username);
        if(accountEntity == null) throw new BusinessException("Không tồn tại account");
        try {
            UsersResource userResource = kcProvider.getKeycloakUserResource();
            UserResource user = userResource.get(accountEntity.getKeycloakId());
            user.logout();
            return "Logout thành công";
        } catch (Exception e) {
            throw new BusinessException("Lỗi khi logout: " + e.getMessage());
        }
    }
}
