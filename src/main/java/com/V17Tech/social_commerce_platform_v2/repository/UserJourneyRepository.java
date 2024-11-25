package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.UserJourneyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJourneyRepository extends JpaRepository<UserJourneyEntity, Long> {
    UserJourneyEntity getFirstBySessionId(String sessionId);
    UserJourneyEntity getFirstById(Long id);

    @Query(value = "select u from UserJourneyEntity u where u.username = :username")
    List<UserJourneyEntity> getAllByUsername(String username);

    UserJourneyEntity getFirstBySessionIdAndUsername(String sessionId, String username);
}
