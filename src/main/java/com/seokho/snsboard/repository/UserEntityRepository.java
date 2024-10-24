package com.seokho.snsboard.repository;

import com.seokho.snsboard.model.entity.PostEntity;
import com.seokho.snsboard.model.entity.UserEntity;
import com.seokho.snsboard.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

}
