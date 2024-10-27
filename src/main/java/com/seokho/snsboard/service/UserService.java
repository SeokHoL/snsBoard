package com.seokho.snsboard.service;


import com.seokho.snsboard.exception.user.UserAlreadyExistsException;
import com.seokho.snsboard.exception.user.UserNotAllowedException;
import com.seokho.snsboard.exception.user.UserNotFoundException;
import com.seokho.snsboard.model.entity.UserEntity;
import com.seokho.snsboard.model.user.User;
import com.seokho.snsboard.model.user.UserAuthenticationResponse;
import com.seokho.snsboard.model.user.UserPatchRequestBody;
import com.seokho.snsboard.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private  JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(
                ()-> new UserNotFoundException(username));

    }

    public User signUp(String username, String password) {
        userEntityRepository.findByUsername(username)
                .ifPresent(user -> {throw  new UserAlreadyExistsException();
                });

        var userEntity = userEntityRepository.save(UserEntity.of(username, passwordEncoder.encode(password)));
        return User.from(userEntity);

    }

    public UserAuthenticationResponse authenticate(String username, String password) {

        var userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));

        if (passwordEncoder.matches(password, userEntity.getPassword())) {
          var accessToken =  jwtService.generateToken(userEntity);
          return new UserAuthenticationResponse(accessToken);
        }else {
            throw new UserNotFoundException();


        }
    }

    public List<User> getUsers(String query) {
        List<UserEntity> userEntities;
        
        if (query !=null && !query.isBlank()){
            //TODO: query 검색어기반, 해당 검색어가 username에 포함되어 있는 유저목록 가져오기
            userEntities =  userEntityRepository.findByUsernameContaining(query);
        }else {
            userEntities = userEntityRepository.findAll();

        }
        return userEntities.stream().map(User::from).toList();
    }

    public User getUser(String username) {
        var userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));

        return User.from(userEntity);

    }

    public User updateUser(String username, UserPatchRequestBody userPatchRequestBody, UserEntity currentUser) {
        var userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));

        if(!userEntity.equals(currentUser)){
            throw new UserNotAllowedException();
        }

        if(userPatchRequestBody.description() !=null){
            userEntity.setDescription(userPatchRequestBody.description());
        }

        return User.from(userEntityRepository.save(userEntity));
    }
}