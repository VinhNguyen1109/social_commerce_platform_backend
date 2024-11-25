package com.V17Tech.social_commerce_platform_v2.service;

import com.V17Tech.social_commerce_platform_v2.entity.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.model.UserEvent;
import com.V17Tech.social_commerce_platform_v2.model.UserJourneyDTO;

import java.io.IOException;
import java.util.List;

public interface UserJourneyService {

    UserJourneyDTO saveUserJourney(UserEvent userEvent, String token, String sessionId) throws IOException;

    UserJourneyDTO getUserJourneyByUsernameAndSession(String token, String sessionId) throws IOException;

    List<UserJourneyDTO> getAllUserJourneyOfUser(String token) throws IOException;

}
