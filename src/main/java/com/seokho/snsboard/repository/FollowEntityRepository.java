package com.seokho.snsboard.repository;

import com.seokho.snsboard.model.entity.FollowEntity;
import com.seokho.snsboard.model.entity.LikeEntity;
import com.seokho.snsboard.model.entity.PostEntity;
import com.seokho.snsboard.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {

    List<FollowEntity> findByFollower(UserEntity follower);

    List<FollowEntity> findByFollowing(UserEntity following);

    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity  following);
}
