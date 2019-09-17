package com.mtx.system.common.email;

import com.mtx.common.constant.SystemConstant;
import com.mtx.common.util.base.PropertiesFileUtil;
import com.mtx.system.dao.dto.SystemUserDto;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/* 邮件发送 */
@Slf4j
public class SimpleMailSender {

    //发送邮件的处理
    public void sendEmail(SystemUserDto systemUserDto) {
        String to  =systemUserDto.getEmail();
        String indexurl= PropertiesFileUtil.getInstance().get("upms.sso.server.url");
        String content=systemUserDto.getNickName()+",你好！<br>&nbsp;&nbsp;感谢你使用i助理(<a href="+indexurl+">"+indexurl+"</a>)<br><b>&nbsp;&nbsp;请点击以下链接完成身份验证：</b><br>"+
                "&nbsp;&nbsp;<a href="+indexurl+"/system/activeEmail?emailToken="+systemUserDto.getEmailToken()+"&email="+to+">"+indexurl
                +"/system/activeEmail?emailToken="+systemUserDto.getEmailToken()+"&email="+to+"</a>";

        MailSenderInfo mailInfo=new MailSenderInfo();
        mailInfo.setContent(content);
        mailInfo.setToAddress(to);
        mailInfo.setFromAddress(SystemConstant.FROM_ADDRESS);
        mailInfo.setSubject("i助理注册确认邮件");
        mailInfo.setMailServerHost(SystemConstant.FROM_ADDRESS_SMTP);
        mailInfo.setMailServerPort(SystemConstant.FROM_ADDRESS_PORT);
        mailInfo.setValidate(true);
        mailInfo.setPassword(SystemConstant.FROM_ADDRESS_PWD);
        mailInfo.setUserName(SystemConstant.FROM_ADDRESS);

        try {
            sendHtmlMail(mailInfo);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 以文本格式发送邮件
     * @param mailInfo 待发送的邮件的信息
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) throws Exception{
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mailInfo.getToAddress());
        mailMessage.setRecipient(Message.RecipientType.TO,to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // 设置邮件消息的主要内容
        String mailContent = mailInfo.getContent();
        mailMessage.setText(mailContent);
        // 发送邮件
        Transport.send(mailMessage);
        return true;
    }

    /**
     * 以HTML格式发送邮件
     * @param mailInfo 待发送的邮件信息
     */
    public  boolean sendHtmlMail(MailSenderInfo mailInfo) throws Exception{
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        Address to = new InternetAddress(mailInfo.getToAddress());
        // Message.RecipientType.TO属性表示接收者的类型为TO
        mailMessage.setRecipient(Message.RecipientType.TO,to);
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        //mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容
        html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);
        // 发送邮件
        Transport.send(mailMessage);
        return true;
    }


}
