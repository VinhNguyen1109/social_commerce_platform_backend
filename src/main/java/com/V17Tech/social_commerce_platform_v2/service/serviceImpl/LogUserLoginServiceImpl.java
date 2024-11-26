package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.LogUserLogin;
import com.V17Tech.social_commerce_platform_v2.repository.LogUserLoginRepository;
import com.V17Tech.social_commerce_platform_v2.service.LogUserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogUserLoginServiceImpl implements LogUserLoginService {
    private final LogUserLoginRepository logUserLoginRepository;
    @Override
    public void saveLogLogin(LogUserLogin login) {
        logUserLoginRepository.save(login);
    }

    @Override
    public List<LogUserLogin> getByUserNameQuery(String name) {
        return logUserLoginRepository.findByUsernameByQuery(name);
    }

}
