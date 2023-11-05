package com.thesis.userservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "id_User")
    private User user;
}
