package com.thesis.userservice.controller;


import com.thesis.userservice.DTO.ViolateAccountDTO;
import com.thesis.userservice.config.UserAuthProvider;
import com.thesis.userservice.DTO.CredentialsDto;
import com.thesis.userservice.DTO.RegisterDto;
import com.thesis.userservice.DTO.UserDto;
import com.thesis.userservice.entity.User;
import com.thesis.userservice.mappers.UserMapper;
import com.thesis.userservice.repository.UserRepository;
import com.thesis.userservice.service.JwtService;
import com.thesis.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserAuthProvider userAuthProvider;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserMapper userMapper;

  //  @GetMapping("/listUser")
//  public List<User> getAllUser() {
//    return userService.getAllUser();
//  }
  @GetMapping("{id}")
  public ResponseEntity<UserDto> getInfor(@PathVariable int id) {
    return ResponseEntity.ok(userService.getEmail(id));
  }

  @GetMapping("/isadmin/{id}")
  public  boolean isAdmin(@PathVariable int id){
    return userService.isAdmin(id);
  }
  @PostMapping("/login")
  public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
    UserDto user = userService.login(credentialsDto);
    user.setToken(userAuthProvider.createToken(user.getUsername()));
//    user.setToken(jwtService.generateToken(user.getUsername()));
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
    UserDto user = userService.register(registerDto);
    user.setToken(userAuthProvider.createToken(user.getUsername()));
    return ResponseEntity.created(URI.create("/users" + user.getId()))
            .body(user);
  }
  @PostMapping("/active-account/{username}")
  public ResponseEntity<UserDto> register(@PathVariable String username) {
    userService.activeSeller(username);
    Optional<User> user = userRepository.findByUsername(username);
    UserDto userDTO = userMapper.toUserDto(user.get());
    userDTO.setToken(jwtService.generateToken(username));
    return ResponseEntity.ok(userDTO);
  }
  @PostMapping("/disable-account")
  public ResponseEntity<UserDto> register(@RequestBody ViolateAccountDTO violateAccountDTO) {
    userService.disableAccount(violateAccountDTO);
    Optional<User> user = userRepository.findByUsername(violateAccountDTO.getUsername());
    return ResponseEntity.ok(userMapper.toUserDto(user.get()));
  }
  @GetMapping("/validate")
  public String validateToken(@RequestParam("token") String token) {
    jwtService.validateToken(token);
    return "Token is valid";
  }
}
