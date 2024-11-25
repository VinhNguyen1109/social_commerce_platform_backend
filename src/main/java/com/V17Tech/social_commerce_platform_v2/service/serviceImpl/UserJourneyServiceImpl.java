package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.model.UserEvent;
import com.V17Tech.social_commerce_platform_v2.model.UserJourneyDTO;
import com.V17Tech.social_commerce_platform_v2.repository.UserJourneyRepository;
import com.V17Tech.social_commerce_platform_v2.service.UserJourneyService;
import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJourneyServiceImpl implements UserJourneyService {

    private final UserJourneyRepository userJourneyRepository;


    @Override
    public UserJourneyDTO saveUserJourney(UserEvent userEvent, String token, String sessionID) throws IOException {
        String username = CommonUtil.getUserNameFromToken(token);
        if(username == null) throw new BusinessException("Không tồn tại người dùng");
        UserJourneyEntity userJourney = userJourneyRepository.getFirstBySessionId(sessionID);
        LinkedList<UserEvent> listEvent;
        if(userJourney == null){
            listEvent = new LinkedList<>();
            listEvent.add(userEvent);
            userJourney = UserJourneyEntity.builder()
                    .data(CommonUtil.serializeToString(listEvent))
                    .sessionId(sessionID)
                    .username(username)
                    .build();
        }else {
            listEvent = (LinkedList<UserEvent>) CommonUtil.deserializeFromString(userJourney.getData(), LinkedList.class);
            listEvent.add(userEvent);
            userJourney.setData(CommonUtil.serializeToString(listEvent));
        }
        userJourneyRepository.save(userJourney);
        return UserJourneyDTO.builder()
                .flow(listEvent)
                .username(username)
                .build();
    }

    @Override
    public UserJourneyDTO getUserJourneyByUsernameAndSession(String token, String sessionId) throws IOException {
        String username = CommonUtil.getUserNameFromToken(token);
        if(username == null) throw new BusinessException("Không tồn tại nguời dùng");
        UserJourneyEntity userJourney = userJourneyRepository.getFirstBySessionIdAndUsername(sessionId, username);
        return UserJourneyDTO.builder()
                .flow((LinkedList<UserEvent>) CommonUtil.deserializeFromString(userJourney.getData(), LinkedList.class))
                .username(userJourney.getUsername())
                .build();
    }
    @Override
    public List<UserJourneyDTO> getAllUserJourneyOfUser(String token) throws IOException {
        String username = CommonUtil.getUserNameFromToken(token);
        if(username == null) throw new BusinessException("Không tồn tại nguời dùng");
        List<UserJourneyEntity> userJourneyEntities = userJourneyRepository.getAllByUsername(username);
        if(userJourneyEntities == null) return null;
        List<UserJourneyDTO> userJourneyDTOS = new ArrayList<>();
        for (UserJourneyEntity userJourney: userJourneyEntities) {
            userJourneyDTOS.add(UserJourneyDTO.builder()
                            .username(userJourney.getUsername())
                            .flow((LinkedList<UserEvent>) CommonUtil.deserializeFromString(userJourney.getData(), LinkedList.class))
                    .build());
        }
        return userJourneyDTOS;
    }

}
