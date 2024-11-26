package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.service.UserJourneyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-journey")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJourneyController {

    private final UserJourneyService userJourneyService;

    @PostMapping("/save-user-journey")
    public ResponseEntity<?> saveUserJourney(@RequestBody UserJourneyEntity userJourney, @RequestHeader String token, @RequestHeader String sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(userJourneyService.saveUserJourney(userJourney, token, sessionId));
    }

    @GetMapping("/get-user-journey")
    public ResponseEntity<?> getUserJourney(@RequestHeader String token, @RequestHeader String sessionId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(userJourneyService.getUserJourneyByUsernameAndSessionId(token, sessionId));
    }


}
