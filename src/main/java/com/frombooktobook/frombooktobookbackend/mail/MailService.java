package com.frombooktobook.frombooktobookbackend.mail;

import com.frombooktobook.frombooktobookbackend.domain.user.User;
import com.nimbusds.oauth2.sdk.auth.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;


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
        message.setFrom("suddni@naver.com");
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

    public void sendVertifyEmail(String email, String code) throws Exception {
        MimeMessage mimeMessage = createEmailVertifyMessage(email,code);
        sendMail(email,mimeMessage);
    }

    private MimeMessage createEmailVertifyMessage(String email, String code) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("FromBookToBook 이메일 인증 코드입니다.");
        message.setText(createEmailVertifyText(code),"utf-8","html");
        message.setFrom("suddni@naver.com");
        return message;
    }

    private String createEmailVertifyText(String code) {
        String text = "";
        text += "아래 코드를 FromBookToBook 이메일 인증 코드란에 입력해주세요.";
        text += "<br>";
        text += "CODE : <strong>";
        text += code;
        text += "</strong>";

        return text;
    }

    private int makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random random = new Random();
        int checkCode = random.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkCode);
        return checkCode;
    }
}
