package com.V17Tech.social_commerce_platform_v2.service.serviceImpl;

import com.V17Tech.social_commerce_platform_v2.entity.AccountEntity;
import com.V17Tech.social_commerce_platform_v2.entity.Post;
import com.V17Tech.social_commerce_platform_v2.model.PostDTO;
import com.V17Tech.social_commerce_platform_v2.repository.PostRepository;
import com.V17Tech.social_commerce_platform_v2.service.AccountService;
import com.V17Tech.social_commerce_platform_v2.service.AreaService;
import com.V17Tech.social_commerce_platform_v2.service.PostService;
import com.V17Tech.social_commerce_platform_v2.util.BusinessException;
import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final AreaService areaService;
    private final AccountService accountService;
    @Override
    public Post getPostById(Long id) {
        return postRepository.getFirstById(id);
    }

    @Override
    public void savePost(PostDTO postDTO, String token) {
        String username = CommonUtil.getUserNameFromToken(token);
        AccountEntity account = accountService.getFirstByUsername(username);
        if(postDTO.getFromPrice() >= postDTO.getToPrice() || postDTO.getFromPrice() < 0){
            throw new BusinessException("Sai format price với post");
        }
        if(account == null) throw new BusinessException("Không tồn tại tài khoản với username: " + username);
        postRepository.save(Post.builder()
                        .title(postDTO.getTitle())
                        .subTitle(postDTO.getSubTitle())
                        .account(account)
                        .address(postDTO.getAddress())
                        .constructionArea(postDTO.getConstructionArea())
                        .email(postDTO.getEmail())
                        .estateType(postDTO.getEstateType())
                        .description(postDTO.getDescription())
                        .district(areaService.getByCode(postDTO.getDistrictCode()))
                        .ward(areaService.getByCode(postDTO.getWardCode()))
                        .province(areaService.getByCode(postDTO.getProvinceCode()))
                        .images(postDTO.getImages())
                        .fromPrice(postDTO.getFromPrice())
                        .toPrice(postDTO.getToPrice())
                        .priceType(postDTO.getPriceType())
                        .numFloors(postDTO.getNumFloors())
                        .numBedrooms(postDTO.getNumBedrooms())
                        .numToilets(postDTO.getNumToilets())
                        .numOfView(0)
                        .numOfShare(0)
                        .numOfClickContact(0)
                        .numOfLike(0)
                        .phoneNumber(postDTO.getPhoneNumber())
                .build());
    }

    @Override
    public void savePostV2(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<PostDTO> getPostByTitle(String title) {
        List<PostDTO> result = new ArrayList<>();
        for (Post post: postRepository.getPostByTitle(title)
             ) {
            result.add(post.toDTO());
        }
        return result;
    }

    @Override
    public PostDTO verifyPost(Long id, int verify) {
        Post post = postRepository.getFirstById(id);
        if(post == null) throw new BusinessException("Khong ton tai post");
        //1 : dong y
        //2: khong dong y
        //0: dang cho
        post.setVerified(verify);
        return postRepository.save(post).toDTO();
    }

    @Override
    public List<PostDTO> getPostByKeyword(String keyword) {
       List<PostDTO> result = new ArrayList<>();
        for (Post post : postRepository.getPostByKeyword(keyword)) {
            result.add(post.toDTO());
        }
        return result;
    }

    @Override
    public Object getByVerify(String verify) {
        try {
            List<PostDTO> result = new ArrayList<>();
            for (Post post : postRepository.getByVerified(Integer.parseInt(verify))) {
                result.add(post.toDTO());
            }
            return result;
        }catch (NumberFormatException exception){
            throw exception;
        }
    }
}
