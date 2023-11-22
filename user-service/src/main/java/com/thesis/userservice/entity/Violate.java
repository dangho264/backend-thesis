package com.thesis.userservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "violate")
@Data
@Builder
public class Violate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_violate")
    private Integer id;
    @Column
    private String violate_reason;
    @Column
    private LocalDateTime createdAt;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_User")
    private User user;
}
