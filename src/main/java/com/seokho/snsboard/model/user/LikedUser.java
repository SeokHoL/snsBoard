package com.seokho.snsboard.model.user;

import com.seokho.snsboard.model.entity.UserEntity;

import java.time.ZonedDateTime;

public record LikedUser(Long userId, String username, String profile, String description, Long followersCount, Long followingCount,
                        ZonedDateTime createdDateTime, ZonedDateTime updatedDateTime, Boolean isFollowing,
                        Long likedPostId, ZonedDateTime likedDateTime) {



    public static LikedUser from(User user, Long likedPostId, ZonedDateTime likedDateTime){
        return new LikedUser(
                user.userId(),
                user.username(),
                user.profile(),
                user.description(),
                user.followersCount(),
                user.followingCount(),
                user.createdDateTime(),
                user.updatedDateTime(),
                user.isFollowing(),
                likedPostId,
                likedDateTime);
    }


}


