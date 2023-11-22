package com.thesis.userservice.service;


import com.thesis.userservice.DTO.*;
import com.thesis.userservice.entity.Violate;
import com.thesis.userservice.repository.RoleRepository;
import com.thesis.userservice.entity.User;
import com.thesis.userservice.entity.Role;
import com.thesis.userservice.repository.UserRepository;
import com.thesis.userservice.mappers.UserMapper;
import com.thesis.userservice.repository.ViolateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ViolateRepository violateRepository;
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto getEmail(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional == null && userOptional.isEmpty()) {
            throw new AppException("User not exist", HttpStatus.BAD_REQUEST);
        }
        return userMapper.toUserDto(userOptional.get());
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername()).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(RegisterDto registerDto) {
        Optional<User> userOptional = userRepository.findByUsername(registerDto.getUsernameRegister());
        if (userOptional.isPresent()) {
            throw new AppException("User is existed", HttpStatus.BAD_REQUEST);
        }
        Optional<Role> userRole = roleRepository.findById(registerDto.getRoleId());
        User user = userMapper.registerToUser(registerDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPasswordRegister())));
        user.setRoles(Arrays.asList(userRole.get()));
        user.setCreatedAt(LocalDateTime.now());
        if (registerDto.getRoleId() == 2) {
            user.setActive(true);
        }
        else if(registerDto.getRoleId()==3){
            user.setActive(false);
        }
        User userSave = userRepository.save(user);
        return userMapper.toUserDto(userSave);
    }

    public boolean isAdmin(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new AppException("Nguoi dung khong ton tai", HttpStatus.NOT_FOUND);
        }
        boolean isAdmin = userOptional.get().getRoles().stream().anyMatch(role -> role.getId() == 1);
        return isAdmin;
    }

    public void activeSeller(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if(!user.isActive()){
            user.setActive(true);
            userRepository.save(user);
        }
        else {
            throw new AppException("Nguoi dung nay da duoc kich hoat", HttpStatus.NOT_MODIFIED);
        }
    }
    public void disableAccount(ViolateAccountDTO violateAccountDTO){
        User user = userRepository.findByUsername(violateAccountDTO.getUsername()).orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if(user.isActive()){
            user.setActive(false);
            Violate violate = new Violate();
            violate.setUser(user);
            violate.setViolate_reason(violateAccountDTO.getReason());
            violate.setCreatedAt(LocalDateTime.now());
            violateRepository.save(violate);
            List<Violate> violateList = new ArrayList<>();
            violateList.add(violate);
            user.setViolates(violateList);
        }
    }
    public Page<User> getUserByRole(int id, Pageable pageable){
        Optional<Role> role = roleRepository.findById(id);
        return userRepository.findAllByRole(role.get(),pageable);
    }
}
