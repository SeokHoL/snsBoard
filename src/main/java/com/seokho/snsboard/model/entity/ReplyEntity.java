package com.seokho.snsboard.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "reply",
indexes = {@Index(name = "reply_userid_idx",columnList = "userid")
        ,@Index(name = "reply_postid_idx",columnList = "postid")})
@SQLDelete(sql = "UPDATE\"reply\"SET deleteddatetime=CURRENT_TIMESTAMP WHERE replyid =?")
@SQLRestriction("deleteddatetime IS NULL")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private ZonedDateTime createdDateTime;

    @Column
    private ZonedDateTime updatedDateTime;

    @Column
    private ZonedDateTime deletedDateTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity post;

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplyEntity that = (ReplyEntity) o;
        return Objects.equals(replyId, that.replyId) && Objects.equals(body, that.body) && Objects.equals(createdDateTime, that.createdDateTime) && Objects.equals(updatedDateTime, that.updatedDateTime) && Objects.equals(deletedDateTime, that.deletedDateTime) && Objects.equals(user, that.user) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyId, body, createdDateTime, updatedDateTime, deletedDateTime, user, post);
    }

    public static ReplyEntity of(String body, UserEntity user,PostEntity post){
        var reply= new ReplyEntity();
        reply.setBody(body);
        reply.setUser(user);
        reply.setPost(post);
        return reply;
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
