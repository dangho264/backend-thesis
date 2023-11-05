package com.thesis.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "Username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "Phone")
    private String phoneNumber;

    @Column(name="email")
    private String email;
    @Column(name="isActive")
    private boolean isActive;
    @Column(name="storeName")
    private String storeName;
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="User_Role", joinColumns = @JoinColumn(name="id_User"), inverseJoinColumns = @JoinColumn(name="ID_Role"))
    private List<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Violate> violates = new ArrayList<>();


  // getters và setters
}
