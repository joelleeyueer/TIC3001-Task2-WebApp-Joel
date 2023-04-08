package nus.task2.Components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import nus.task2.Models.UserInfo;
import nus.task2.Models.UseridPassword;
import nus.task2.Repositories.UserInfoRepository;

@Component
public class UserInfoUserDetailsComponent implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByName(username);
        return userInfo.map(UseridPassword::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}
