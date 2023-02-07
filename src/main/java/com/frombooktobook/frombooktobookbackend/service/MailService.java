package com.frombooktobook.frombooktobookbackend.service;

import com.frombooktobook.frombooktobookbackend.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;


@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.redis.expireMinutes}")
    private long expireMin;
    @Value("${spring.mail.from}")
    private String fromAddress;

    public void sendMail(String email, MimeMessage message) throws Exception{
        try{
            javaMailSender.send(message);
        } catch(MailException mailException) {
            mailException.printStackTrace();
            throw new IllegalAccessException();
        }
    }

    public void sendTempPasswordEmail(String email,String tempPassword) throws Exception{
            MimeMessage mimeMessage = createTempPasswordMessage(email,tempPassword);
            sendMail(email,mimeMessage);
    }

    private MimeMessage createTempPasswordMessage(String email,String tempPassword) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("FromBookToBook 요청하신 임시 비밀번호입니다.");
        message.setText(createTempPasswordText(tempPassword),"utf-8","html");
        message.setFrom(fromAddress);
        return message;
    }

    private String createTempPasswordText(String tempPassword) {
        String text = "";
        text += "FrombookToBook에서 요청하신 임시 비밀번호를 보내드립니다.";
        text += "<br>";
        text += "임시 비밀번호 : <strong>";
        text += tempPassword;
        text += "</strong>";
        text += "<br>";
        text += "로그인 하신 뒤, 빠르게 비밀번호를 변경해주시기 바랍니다.";

        return text;
    }

    public void sendVerifyEmail(String email, String code) throws Exception {
        MimeMessage mimeMessage = createEmailVerifyMessage(email,code);
        sendMail(email,mimeMessage);
        redisUtil.setData(code,email,expireMin);
    }

    private MimeMessage createEmailVerifyMessage(String email, String code) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("FromBookToBook 이메일 인증 코드입니다.");
        message.setText(createEmailVerifyText(code),"utf-8","html");
        message.setFrom(new InternetAddress(fromAddress,"FromBookToBook"));
        return message;
    }

    private String createEmailVerifyText(String code) {
        String text = "";
        text += "아래 코드를 FromBookToBook 이메일 인증 코드란에 입력해주세요.";
        text += "<br>";
        text += "CODE : <strong>";
        text += code;
        text += "</strong>";

        return text;
    }

    public boolean verifyEmailCode(String code) {
        String email = redisUtil.getData(code);
        if(email == null) {
            return false;
        }
        return true;
    }

    private int makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random random = new Random();
        int checkCode = random.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkCode);
        return checkCode;
    }
}
