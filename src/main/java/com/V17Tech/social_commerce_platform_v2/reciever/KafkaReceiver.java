package com.V17Tech.social_commerce_platform_v2.reciever;

import com.V17Tech.social_commerce_platform_v2.constant.ActionPostEnum;
import com.V17Tech.social_commerce_platform_v2.entity.Post;
import com.V17Tech.social_commerce_platform_v2.entity.mongo.UserJourneyEntity;
import com.V17Tech.social_commerce_platform_v2.model.SendEmailDTO;
import com.V17Tech.social_commerce_platform_v2.model.UserClickContactDTO;
import com.V17Tech.social_commerce_platform_v2.repository.UserJourneyRepository;
import com.V17Tech.social_commerce_platform_v2.service.EmailService;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaReceiver {
    private final ObjectMapper objectMapper;
    private final PostService postService;

    private final UserJourneyRepository userJourneyRepository;

    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    @KafkaListener(topics = "${spring.kafka.topics.test-kafka}")
    public void checkKafka (String message){
        System.out.println("check message: " + message);
    }

    @KafkaListener(topics = "${spring.kafka.topics.post-click-contact}")
    public void changeClickContactOfPost(String data){
        logger.info(data);
        try {
          UserClickContactDTO userClickContactDTO =  objectMapper.readValue(data, UserClickContactDTO.class);
            Long postId = userClickContactDTO.getPostId();
            Post post = postService.getPostById(postId);

            switch (ActionPostEnum.fromValue(userClickContactDTO.getType())) {
                case CLICK:
                    post.setNumOfClickContact(post.getNumOfClickContact() + 1);
                    post.setNumOfView(post.getNumOfView() + 1);
                    postService.savePostV2(post);
                    logger.info("add click contact to post have id: " + postId);
                    break;
                case LIKE:
                    post.setNumOfLike(post.getNumOfLike() + 1);
                    post.setNumOfView(post.getNumOfView() + 1);
                    postService.savePostV2(post);
                    logger.info("add like to post have id: " + postId);
                    break;
                case SHARE:
                    post.setNumOfShare(post.getNumOfShare() + 1);
                    post.setNumOfView(post.getNumOfView() + 1);
                    postService.savePostV2(post);
                    logger.info("add share to post have id: " + postId);
                    break;
                case VIEW:
                    post.setNumOfView(post.getNumOfView() + 1);
                    postService.savePostV2(post);
                    logger.info("add view to post have id: " + postId);
                default:
                    logger.info("not correct type");
                    break;
            }
        }catch (JsonProcessingException e){
            logger.info("can not map data");
        }
    }
    @KafkaListener(topics = "${spring.kafka.topics.user-journey}")
    public void saveUserJourney(String data){
        try {
            UserJourneyEntity userJourney =  objectMapper.readValue(data, UserJourneyEntity.class);
            userJourney.setStatus(0);
            userJourney.setTime(new Date());
            userJourneyRepository.save(userJourney);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @KafkaListener(topics = "${spring.kafka.topics.send-email}")
    public void sendEmail(String data){
        try {
            SendEmailDTO sendEmailDTO =  objectMapper.readValue(data, SendEmailDTO.class);
            logger.info("listen topic");
            emailService.sendSimpleEmail(sendEmailDTO.getToEmail(),
                    sendEmailDTO.getFromEmail(),
                    sendEmailDTO.getTitle(),
                    sendEmailDTO.getContent());
        }catch (JsonProcessingException exception){
            logger.error("Lỗi khi mapper data qua kafka");
        }
    }
}
