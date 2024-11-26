package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.mongo.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.repository.UserJourneyRepository;
import com.V17Tech.social_commerce_platform_v2.sender.KafkaSender;
import com.V17Tech.social_commerce_platform_v2.service.UserJourneyService;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJourneyServiceImpl implements UserJourneyService {
    private final Logger logger = LoggerFactory.getLogger(UserJourneyServiceImpl.class);
    private final UserJourneyRepository userJourneyRepository;

    private final RedisTemplate<String, String> redisTemplate;


    private final MongoTemplate mongoTemplate;

    private final ObjectMapper objectMapper;
    @Value("${spring.kafka.topics.user-journey}")
    private String UserJourneyTopic;
    private final KafkaSender sender;
    @Override
    public UserJourneyEntity saveUserJourney(UserJourneyEntity userJourney, String token, String sessionId) {
        String username = CommonUtil.getUserNameFromToken(token);
        userJourney.setUsername(username);
        sender.send(UserJourneyTopic, userJourney);
        userJourney.setTime(new Date());
        ObjectMapper objectMapper = new ObjectMapper();
        String userJourneyJson = null;
        try {
            userJourneyJson = objectMapper.writeValueAsString(userJourney);
        } catch (JsonProcessingException e) {
            logger.error("Lỗi khi chuyển đổi userJourney thành JSON: " + e.getMessage());
        }
        logger.info("JOURNEY_" + username + sessionId);
        redisTemplate.opsForList().rightPush("JOURNEY_" + username + sessionId, userJourneyJson);
        return userJourney;
    }

    @Override
    public List<UserJourneyEntity> getUserJourneyByUsernameAndSessionId(String token, String sessionId) throws JsonProcessingException {
        String username = CommonUtil.getUserNameFromToken(token);
        logger.info("JOURNEY_" + username + sessionId);
        List<String> cacheUserJourney = redisTemplate.opsForList().range("JOURNEY_" + username + sessionId, 0, -1);
        List<UserJourneyEntity> rlt;
        if(cacheUserJourney.size() > 0){
            logger.info("get data from cahce");
            rlt = new ArrayList<>();
            for (String data: cacheUserJourney) {
                logger.info(data);
                UserJourneyEntity userJourney =  objectMapper.readValue(data, UserJourneyEntity.class);
                rlt.add(userJourney);
            }
        }else {
            logger.info("username: " + username);
            logger.info("sessionId: " + sessionId );
            Criteria cre = new Criteria("username").is(username)
                    .and("sessionId").is(sessionId);
            MatchOperation matchStage = Aggregation.match(cre);
            SortOperation sortByTime = Aggregation.sort(Sort.by(Sort.Direction.ASC, "time"));
            Aggregation aggregation = Aggregation.newAggregation(matchStage, sortByTime);
            AggregationResults<UserJourneyEntity> result = mongoTemplate
                    .aggregate(aggregation, "user-journey-flow", UserJourneyEntity.class);
            logger.info(result.getMappedResults().size() + "");
            rlt = new LinkedList<>(result.getMappedResults());
        }
        return rlt;
    }
    @Override
    public List<UserJourneyEntity> getAll() {
        return userJourneyRepository.findAll();
    }

}
