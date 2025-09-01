package com.bus.auth_ms.service;

import com.bus.auth_ms.entity.Role;
import com.bus.auth_ms.entity.User;
import com.bus.auth_ms.repository.RoleRepository;
import com.bus.auth_ms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles().stream()
                .map(r -> roleRepo.findByName(r.getName())
                        .orElseGet(() -> roleRepo.save(new Role(r.getName()))))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userRepo.save(user);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public java.util.Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
