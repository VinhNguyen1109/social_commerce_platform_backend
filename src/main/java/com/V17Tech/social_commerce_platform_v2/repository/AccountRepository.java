package com.V17Tech.social_commerce_platform_v2.repository;


import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

     AccountEntity getFirstByUsername(String username);

     Boolean existsByUsernameAndPassword(String username, String password);

     @Query("select count(a) from AccountEntity a")
     Long countById();

     @Query("select a from AccountEntity a where a.id > :from and a.id <= :to")
     List<AccountEntity> getAccountListToSendNotification(@Param("from") Long from, @Param("to") Long to);

}
