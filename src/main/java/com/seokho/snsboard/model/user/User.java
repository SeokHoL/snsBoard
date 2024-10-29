package com.seokho.snsboard.model.user;

import com.seokho.snsboard.model.entity.UserEntity;

import java.time.ZonedDateTime;

public record User(Long userId, String username, String profile, String description, Long followersCount, Long followingCount,
                   ZonedDateTime createdDateTime, ZonedDateTime updatedDateTime, Boolean isFollowing) {

    public static User from(UserEntity userEntity){
        return new User(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getProfile(),
                userEntity.getDescription(),
                userEntity.getFollowesrCount(),
                userEntity.getFollowingsCount(),
                userEntity.getCreatedDateTime(),
                userEntity.getUpdatedDateTime(),
                null);
    }

    public static User from(UserEntity userEntity, Boolean isFollowing){
        return new User(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getProfile(),
                userEntity.getDescription(),
                userEntity.getFollowesrCount(),
                userEntity.getFollowingsCount(),
                userEntity.getCreatedDateTime(),
                userEntity.getUpdatedDateTime(),
                isFollowing);
    }


}


