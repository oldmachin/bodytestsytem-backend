package org.example.controller;

import jakarta.validation.constraints.NotBlank;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.model.user.User;
import org.example.service.AdministratorService;
import org.example.service.AuthService;
import org.example.service.StudentService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new LoginResponse(null, null, null, "用户名或密码错误"));
        }

        String jwt = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        // 这里你需要根据实际的用户实体来填充 LoginResponse 中的其他信息
        // 假设你的 UserDetails 实现了可以获取 ID 和 Email 的接口或可以转型
        // 或者从 userRepository 再次查询
        // 假设我们从 userDetails 中获取
        Long userId = null; // 实际项目中从 userDetails 或数据库获取
        String email = null; // 实际项目中从 userDetails 或数据库获取
        // 假设 MyUserDetailsService 返回的 UserDetails 可以向下转型为自定义的 MyUserDetails
        // 或者从数据库查询 User user = userRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        // ...然后填充 userId, email 等

        return ResponseEntity.ok(new LoginResponse(jwt, "登录成功", userId, loginRequest.getUsername(), email, null));
    }
}
