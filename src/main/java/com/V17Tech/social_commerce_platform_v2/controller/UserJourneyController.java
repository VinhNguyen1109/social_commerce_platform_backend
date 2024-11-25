package com.V17Tech.social_commerce_platform_v2.controller;

import com.V17Tech.social_commerce_platform_v2.model.UserEvent;
import com.V17Tech.social_commerce_platform_v2.service.UserJourneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/user-journey")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJourneyController {

    private final UserJourneyService userJourneyService;

    @PostMapping("/save-user-journey")
    public ResponseEntity<?> saveUserJourney(@RequestBody UserEvent userEvent, @RequestHeader String token, @RequestHeader String sessionId) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userJourneyService.saveUserJourney(userEvent, token, sessionId));
    }

    @GetMapping("/get-in-session")
    public ResponseEntity<?> getBySessionId( @RequestHeader String token, @RequestHeader String sessionId) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userJourneyService.getUserJourneyByUsernameAndSession(token, sessionId));
    }

    @GetMapping("/get-all-by-username")
    public ResponseEntity<?> getByUsername( @RequestHeader String token) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userJourneyService.getAllUserJourneyOfUser(token));
    }
}
