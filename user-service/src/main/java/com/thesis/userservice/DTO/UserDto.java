package com.thesis.userservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesis.userservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
}
