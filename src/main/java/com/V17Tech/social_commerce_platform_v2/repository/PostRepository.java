package com.V17Tech.social_commerce_platform_v2.repository;

import com.V17Tech.social_commerce_platform_v2.entity.Post;
import com.V17Tech.social_commerce_platform_v2.model.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post getFirstById(Long id);

    List<Post> getPostByTitle(String title);
    @Query(value = "select p from Post p where p.title like %:keyword% or p.subTitle like %:keyword%")
    List<Post> getPostByKeyword(String keyword);

    List<Post> getByVerified(int verified);

    List<Post> findTop3ByOrderByRankingPointDesc();

    @Query(value = "select new com.V17Tech.social_commerce_platform_v2.model.PostDTO(p.id, p.title, p.subTitle, " +
            "p.description, p.address, p.ward.code, p.district.code, p.province.code, " +
            "p.account.username, p.phoneNumber, p.zalo, p.email, p.type, p.estateType, p.fromPrice, p.toPrice, p.priceType," +
            "p.constructionArea, p.numBedrooms, p.numToilets, p.numFloors, p.images, p.videos, p.status, " +
            "p.positions, p.visibleFrom, p.visibleTo, p.numOfView, p.numOfShare, p.numOfLike, p.numOfClickContact," +
            "p.verified, p.rankingPoint)" +
            " from Post p")
    Page<PostDTO> findAllToDTO(Pageable pageable);
}
