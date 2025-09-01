package com.bus.auth_ms.controller;

import com.bus.auth_ms.entity.Role;
import com.bus.auth_ms.entity.User;
import com.bus.auth_ms.service.UserService;
import com.bus.auth_ms.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody Map<String, Object> payload) {
//        @SuppressWarnings("unchecked")
//        List<String> roleNames = (List<String>) payload.get("roles");
//
//        Set<Role> roles = roleNames.stream()
//                .map(Role::new) // just create Role with name; id will be null
//                .collect(Collectors.toSet());
//
//        User user = User.builder()
//                .username((String) payload.get("username"))
//                .email((String) payload.get("email"))
//                .password((String) payload.get("password"))
//                .roles(roles)
//                .build();
//
//        return ResponseEntity.ok(userService.saveUser(user));
//    }




//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
//        String username = payload.get("username");
//        String password = payload.get("password");
//
//        return userService.findByUsername(username).map(user -> {
//            if (userService.passwordMatches(password, user.getPassword())) {
//                String token = jwtUtil.generateToken(username);
//                return ResponseEntity.ok(Map.of("token", token));
//            } else {
//                return ResponseEntity.status(401).body("Invalid credentials");
//            }
//        }).orElse(ResponseEntity.status(401).body("User not found"));
//    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, Object> payload) {
        @SuppressWarnings("unchecked")
        List<String> roleNames = (List<String>) payload.get("roles");

        Set<Role> roles = roleNames.stream()
                .map(Role::new)
                .collect(Collectors.toSet());

        User user = User.builder()
                .username((String) payload.get("username"))
                .email((String) payload.get("email"))
                .password((String) payload.get("password")) // raw password here
                .roles(roles)
                .build();

        return ResponseEntity.ok(userService.saveUser(user)); // encoding happens here
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        return userService.findByUsername(username).map(user -> {
            if (userService.passwordMatches(password, user.getPassword())) {
                String token = jwtUtil.generateToken(username);
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        }).orElse(ResponseEntity.status(401).body("User not found"));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
