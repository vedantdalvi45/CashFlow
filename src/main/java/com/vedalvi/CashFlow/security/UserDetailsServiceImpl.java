package com.vedalvi.CashFlow.security;


import com.vedalvi.CashFlow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here, username is the email
        return userRepository.findByEmail(username)
                .map(CustomUserDetails::new) // Wrap the User object
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
