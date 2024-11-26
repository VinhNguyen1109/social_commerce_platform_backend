package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.UserJourneyEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;
import java.util.List;

public interface UserJourneyService {
     List<UserJourneyEntity> getAll();
     UserJourneyEntity saveUserJourney(UserJourneyEntity userJourney, String token, String sessionId);

     List<UserJourneyEntity> getUserJourneyByUsernameAndSessionId(String token, String sessionId) throws JsonProcessingException;
}
