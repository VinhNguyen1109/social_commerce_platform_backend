package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.UserJourneyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJourneyRepository extends MongoRepository<UserJourneyEntity, String> {
   UserJourneyEntity findBySessionId(String sessionId);
   UserJourneyEntity findBySessionIdAndUsername(String sessionId, String username);

}
