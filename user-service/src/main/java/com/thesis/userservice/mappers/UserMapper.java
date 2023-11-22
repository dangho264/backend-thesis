package com.thesis.userservice.mappers;


import com.thesis.userservice.DTO.RegisterDto;
import com.thesis.userservice.DTO.UserDto;
import com.thesis.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserDto toUserDto(User user) {
    UserDto userDto = UserDto.builder()
            .username(user.getUsername())
            .id(user.getId())
            .name(user.getName())
            .password(user.getPassword())
            .roles(user.getRoles())
            .role(user.getRoles().get(0).getName())
            .storeName(user.getStoreName())
            .createdAt(user.getCreatedAt())
            .email(user.getEmail())
            .phonenumber(user.getPhoneNumber())
            .isActive(user.isActive())
            .build();
    return userDto;
  }

  public User registerToUser(RegisterDto signUpDto) {
    User user = new User();
    user.setPassword(signUpDto.getPasswordRegister());
    user.setUsername(signUpDto.getUsernameRegister());
    user.setPhoneNumber(signUpDto.getPhoneNumber());
    user.setName(signUpDto.getName());
    user.setEmail(signUpDto.getEmail());
    user.setStoreName(signUpDto.getStoreName());
    return user;
  }
}
