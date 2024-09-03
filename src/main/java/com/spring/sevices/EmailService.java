package com.spring.sevices;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {

    void sendEmail(String to, String subject, String templateName, Context context) throws MessagingException;
}
