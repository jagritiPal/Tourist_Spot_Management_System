package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Admin;
import com.tourist_spot_management.entity.Role;
import com.tourist_spot_management.entity.User;
import com.tourist_spot_management.entity.Guide;
import com.tourist_spot_management.payload.LoginRequest;
import com.tourist_spot_management.repository.AdminRepository;
import com.tourist_spot_management.repository.UserRepository;
import com.tourist_spot_management.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        String password = loginRequest.getPassword();

        Admin admin = adminRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
            return "Admin logged in successfully";
        }

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "User logged in successfully";
        }

        Guide guide = guideRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (guide != null && passwordEncoder.matches(password, guide.getPassword())) {
            return "Guide logged in successfully";
        }

        return "Invalid username/email or password";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsernameOrEmail(username, username);
        if (admin != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), authorities);
        }

        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }

        Guide guide = guideRepository.findByUsernameOrEmail(username, username);
        if (guide != null) {
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_GUIDE"));
            return new org.springframework.security.core.userdetails.User(guide.getUsername(), guide.getPassword(), authorities);
        }

        throw new UsernameNotFoundException("User not found with username or email: " + username);
    }

    private Collection< ? extends GrantedAuthority>
    mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
