package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.LogUserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogUserLoginRepository extends MongoRepository<LogUserLogin, String> {
    @Query("{'username' : ?0}")
    List<LogUserLogin> findByUsernameByQuery(String username);
}
