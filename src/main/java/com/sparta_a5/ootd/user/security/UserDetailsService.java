package com.sparta_a5.ootd.user.security;

import com.sparta_a5.ootd.user.entity.User;
import com.sparta_a5.ootd.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImpl getUserDetails(String username)  {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));
        return new UserDetailsImpl(user);
    }
}