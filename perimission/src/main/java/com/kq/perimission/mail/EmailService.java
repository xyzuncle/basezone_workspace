package com.kq.perimission.mail;

import com.google.common.base.Charsets;
import com.kq.common.exception.BaseException;
import com.kq.perimission.dto.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${spring.mail.title}")
    private String emailTitle;

    @Autowired
    private JavaMailSender mailSender;

   /* @Autowired
    private VelocityEngine velocityEngine11;*/

    /**
     * 发送简单的邮件
     * @param sendTo
     * @param titel
     * @param content
     */
    public void sendSimpleMail(String sendTo, String titel, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(sendTo);
        message.setSubject(titel);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     * @param sendTo
     * @param titel
     * @param content
     * @param attachments
     */
    public void sendAttachmentsMail(String sendTo, String titel, String content, List<Pair<String, File>> attachments) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject(titel);
            helper.setText(content);

            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getLeft(), new FileSystemResource(pair.getRight()));
            }
        } catch (Exception e) {
            throw new BaseException("发送邮件失败",500);
        }

        mailSender.send(mimeMessage);
    }



    public void sendForgetpw(String tempCode, String sendTo) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.addRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("13699200916@163.com"));
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");

        helper.setFrom(emailFrom);
        helper.setTo(sendTo);
        helper.setSubject("陆地观测卫星数据服务平台密码找回");
        helper.setText("您好，感谢您注册和使用XXXXXX。\n" +
                "您用于修改密码的验证码为：" +tempCode+"\n"+
                "如有任何疑问或遇到问题，技术支持QQ：4006852216；发送电子邮件至 support@XX.com，请随时与我们联系！\n" +
                "感谢您的使用，期望我们能给您带来满意的体验!");


        mailSender.send(mimeMessage);

    }




}
