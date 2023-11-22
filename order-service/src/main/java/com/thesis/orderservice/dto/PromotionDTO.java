package com.thesis.orderservice.dto;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDTO {
    private String description;
    private String couponCode;
    private double couponPercent;
    private Date startDate;
    private Date endDate;
    private String sellerName;

}
