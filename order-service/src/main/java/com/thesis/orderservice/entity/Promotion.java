package com.thesis.orderservice.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Promotion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    @Column(columnDefinition = "nvarchar(100)")
    private String Description;
    @Column
    private String couponCode;
    @Column
    private float couponPercent;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
}
