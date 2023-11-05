package com.example.ReviewProductService.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private String comment;
    private int product_id;
    private String username;
}
