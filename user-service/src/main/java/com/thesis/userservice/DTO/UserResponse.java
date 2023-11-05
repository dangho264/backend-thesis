package com.thesis.userservice.DTO;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;


@Data
@Builder
public class UserResponse {
  @Column(name = "name")
  private String name;

  @Column(name = "Age")
  private int age;

  @Column(name = "Sex")
  private Boolean sex;

  @Column(name = "Year_Of_Birth")
  private int DOB;
}
