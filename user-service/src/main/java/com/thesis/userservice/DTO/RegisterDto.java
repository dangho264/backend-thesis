package com.thesis.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterDto {
  private String usernameRegister;
  private String passwordRegister;
  private String name;
  private String storeName;
  private String phoneNumber;
  private String email;
  private int roleId;

}
