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

    public void sendTempPasswordEmail(String email,String tempPassword) throws Exception{
            MimeMessage mimeMessage = createTempPasswordMessage(email,tempPassword);
            sendMail(email,mimeMessage);
    }


    public void sendMail(String email, MimeMessage message) throws Exception{
        try{
            javaMailSender.send(message);
        } catch(MailException mailException) {
            mailException.printStackTrace();
            throw new IllegalAccessException();
        }
    }

    private MimeMessage createMessage(String email) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("FromBookToBook 이메일 인증 테스트 번호입니다.");
        message.setText("테스트 코드 : "+makeRandomNumber());

        message.setFrom("suddni@naver.com");
        return message;
    }

    private MimeMessage createTempPasswordMessage(String email,String tempPassword) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,email);
        message.setSubject("FromBookToBook 요청하신 임시 비밀번호입니다.");
        message.setText("임시비밀번호 : "+tempPassword);

        message.setFrom("suddni@naver.com");
        return message;
    }

    private int makeRandomNumber() {
        // 난수의 범위 111111 ~ 999999 (6자리 난수)
        Random random = new Random();
        int checkCode = random.nextInt(888888) + 111111;
        System.out.println("인증번호 : " + checkCode);
        return checkCode;
    }
}
