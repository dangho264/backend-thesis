package com.thesis.userservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailUser {
    private String name;
    private String username;
    private String email;
    private String storeName;
    private String violate;
}