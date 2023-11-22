package com.thesis.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesis.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
  private Integer id;
  private String name;
  private String username;
  private String password;
  private String token;
  @JsonIgnore
  private List<Role> roles = new ArrayList<>();
  private String role;
  private String storeName;
  private LocalDateTime createdAt;
  private String email;
  private String phonenumber;
  private Boolean isActive;
}
