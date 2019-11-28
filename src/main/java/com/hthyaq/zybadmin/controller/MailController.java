package com.hthyaq.zybadmin.controller;

import com.hthyaq.zybadmin.model.entity.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/email")
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/sendMail")
    public boolean sendMail(@RequestBody MailBean mailBean) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18256542305@163.com");
        simpleMailMessage.setTo(mailBean.getEmail());
        simpleMailMessage.setSubject("身份认证");
        simpleMailMessage.setText(mailBean.getEmail());
        try {
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
