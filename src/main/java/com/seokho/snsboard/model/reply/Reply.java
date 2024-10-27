package com.seokho.snsboard.model.reply;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.seokho.snsboard.model.entity.PostEntity;
import com.seokho.snsboard.model.entity.ReplyEntity;
import com.seokho.snsboard.model.post.Post;
import com.seokho.snsboard.model.user.User;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Reply(
        Long replyId,
        String body,
        User user,
        Post post,
        ZonedDateTime createdDateTime,
        ZonedDateTime updatedDateTime,
        ZonedDateTime deletedDateTime) {
    public static Reply from(ReplyEntity replyEntity) {
        return new Reply(
                replyEntity.getReplyId(),
                replyEntity.getBody(),
                User.from(replyEntity.getUser()),
                Post.from(replyEntity.getPost()),
                replyEntity.getCreatedDateTime(),
                replyEntity.getUpdatedDateTime(),
                replyEntity.getDeletedDateTime());
    }
}