package com.seokho.snsboard.service;


import com.seokho.snsboard.exception.post.UserNotFoundException;
import com.seokho.snsboard.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserEntityRepository userEntityRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(
                ()-> new UserNotFoundException(username));

    }
}
