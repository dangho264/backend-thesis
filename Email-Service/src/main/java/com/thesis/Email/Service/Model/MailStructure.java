package com.thesis.Email.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MailStructure {
    private String subject;
    private String message;
    private BigDecimal totalPrice;
    private String orderStatus;
}
