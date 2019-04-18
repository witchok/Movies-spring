package com.bootmovies.movies.data.services;

import com.bootmovies.movies.data.repos.UserRepository;
import com.bootmovies.movies.domain.user.User;
import com.bootmovies.movies.domain.user.UserDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class MovieUserDetailsService implements UserDetailsService {
    private static final Logger log = LogManager.getLogger(MovieUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MovieUserDetailsService:loadUserByUsername({})",username);
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            log.info("User '{}' not found",username);
            throw new UsernameNotFoundException("User with name " + username + "not found");
        }
        return new UserDetails(
                user.getId(),
                user.getUsername(),
                user.getEncodedPassword(),
                getGrantedAuthorities(user.getRoles()));
    }

    private static Set<GrantedAuthority> getGrantedAuthorities(Set<String> roles){
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
