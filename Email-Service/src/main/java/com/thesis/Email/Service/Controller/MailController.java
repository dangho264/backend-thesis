package com.thesis.Email.Service.Controller;

import com.thesis.Email.Service.Model.MailStructure;
import com.thesis.Email.Service.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@CrossOrigin
public class MailController {
    @Autowired
    private MailService mailService;
    @PostMapping("/send-mail/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){
        MailStructure mailStructure1 = new MailStructure();
        mailStructure1.setSubject("Xác nhận đặt hàng thành công !");
        String mes = "Đặt hàng thành công\n";
        mes+="Tổng tiền của đơn hàng: "+mailStructure.getTotalPrice();
        mes+="\n Trạng thái đơn hàng là" + mailStructure.getOrderStatus();
        mailStructure1.setMessage(mes);
//        mailService.sendMail(mail, mailStructure1);
        return "Mail send successfully";
    }
}
