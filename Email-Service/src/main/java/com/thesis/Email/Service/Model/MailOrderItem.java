package com.thesis.Email.Service.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailOrderItem {
    public String name;
    public int quantity;
    public BigDecimal price;
}
