package com.seokho.snsboard.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "post",
indexes = {@Index(name = "post_userid_idx",columnList = "userid")})
@SQLDelete(sql = "UPDATE\"post\"SET deleteddatetime=CURRENT_TIMESTAMP WHERE postid =?")
@SQLRestriction("deleteddatetime IS NULL")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private Long repliesCount =0L;

    @Column
    private Long likesCount =0L;

    @Column
    private ZonedDateTime createdDateTime;

    @Column
    private ZonedDateTime updatedDateTime;

    @Column
    private ZonedDateTime deletedDateTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Long repliesCount) {
        this.repliesCount = repliesCount;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ZonedDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(ZonedDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public ZonedDateTime getDeletedDateTime() {
        return deletedDateTime;
    }

    public void setDeletedDateTime(ZonedDateTime deletedDateTime) {
        this.deletedDateTime = deletedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostEntity post = (PostEntity) o;
        return Objects.equals(postId, post.postId) && Objects.equals(body, post.body) && Objects.equals(repliesCount, post.repliesCount) && Objects.equals(likesCount, post.likesCount) && Objects.equals(createdDateTime, post.createdDateTime) && Objects.equals(updatedDateTime, post.updatedDateTime) && Objects.equals(deletedDateTime, post.deletedDateTime) && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, body, repliesCount, likesCount, createdDateTime, updatedDateTime, deletedDateTime, user);
    }

    public static PostEntity of(String body, UserEntity user){
        var post= new PostEntity();
        post.setBody(body);
        post.setUser(user);
        return post;
    }

    @PrePersist
    private void prePersist(){
        this.createdDateTime = ZonedDateTime.now();
        this.updatedDateTime = this.createdDateTime;
    }
    @PreUpdate
    private void preUpdate(){
        this.updatedDateTime = ZonedDateTime.now();
    }

}
