package com.seokho.snsboard.model.user;

import com.seokho.snsboard.model.entity.UserEntity;

import java.time.ZonedDateTime;

public record Follower(Long userId, String username,
                       String profile, String description,
                       Long followersCount, Long followingCount,
                       ZonedDateTime createdDateTime, ZonedDateTime updatedDateTime,
                       Boolean isFollowing ,ZonedDateTime followedDateTime) {


    public static Follower from(User user, ZonedDateTime followedDateTime){
        return new Follower(
                user.userId(),
                user.username(),
                user.profile(),
                user.description(),
                user.followersCount(),
                user.followingCount(),
                user.createdDateTime(),
                user.updatedDateTime(),
                user.isFollowing(),
                followedDateTime);

    }


}


