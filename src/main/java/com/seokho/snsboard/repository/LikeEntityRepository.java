package com.seokho.snsboard.repository;

import com.seokho.snsboard.model.entity.LikeEntity;
import com.seokho.snsboard.model.entity.PostEntity;
import com.seokho.snsboard.model.entity.ReplyEntity;
import com.seokho.snsboard.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {

    List<LikeEntity> findByUser(UserEntity user);

    List<LikeEntity> findByPost(PostEntity post);

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

}
