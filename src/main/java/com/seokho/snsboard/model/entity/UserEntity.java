package com.seokho.snsboard.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.hibernate.grammars.hql.HqlParser.CURRENT_TIMESTAMP;

@Entity
@Table(name = "\"user\"",
indexes = {@Index(name = "user_username_idx", columnList = "username", unique = true)})
@SQLDelete(sql = "UPDATE\"user\" SET deleteddatetime=CURRENT_TIMESTAMP WHERE userid =?")
@SQLRestriction("deleteddatetime IS NULL")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column private String profile;

    @Column private String description;

    @Column private ZonedDateTime createdDateTime;

    @Column private ZonedDateTime updatedDateTime;

    @Column private  ZonedDateTime deletedDatetime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ZonedDateTime getDeletedDatetime() {
        return deletedDatetime;
    }

    public void setDeletedDatetime(ZonedDateTime deletedDatetime) {
        this.deletedDatetime = deletedDatetime;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ZonedDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(ZonedDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(profile, that.profile) && Objects.equals(description, that.description) && Objects.equals(createdDateTime, that.createdDateTime) && Objects.equals(updatedDateTime, that.updatedDateTime) && Objects.equals(deletedDatetime, that.deletedDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, profile, description, createdDateTime, updatedDateTime, deletedDatetime);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserEntity of(String username, String password){
        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        //Avatar Placeholder 서비스(https://avatar-placeholder.iran.liara.run)기반
        //랜덤한 프로필 사진 설정 (1~100)
//        userEntity.setProfile("https://avatar-placeholder.iran.liara.run/public/" + (new Random().nextInt(100) + 1));
        userEntity.setProfile("https://dev-jayce.github.io/public/profile/" + (new Random().nextInt(100) + 1)+".png");
       return userEntity;
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
