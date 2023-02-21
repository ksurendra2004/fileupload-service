package com.fileupload.service;

import com.fileupload.dao.AppUserRepository;
import com.fileupload.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByEmail(s);
        if (user.isPresent()) {
            AppUser appuser = user.get();
            return User.withUsername(appuser.getEmail()).password(appuser.getPassword()).authorities("USER").build();
        } else {
            throw new UsernameNotFoundException(String.format("Email[%s] not found", s));
        }
    }
}
