package com.toyboard.demo.service;

import com.toyboard.demo.dto.UserDto;
import com.toyboard.demo.entity.User;
import com.toyboard.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * @param userDto
     */
    public void createUser(UserDto userDto) {
        User user = null;
        if(userRepository.findByUsername(userDto.getUsername()) == null){
            user = User.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
        }

        userRepository.save(user);

    }

    public User getUser(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user != null && (user.getPassword() == userDto.getPassword())){
            return user;
        }

        return null;
    }
}
