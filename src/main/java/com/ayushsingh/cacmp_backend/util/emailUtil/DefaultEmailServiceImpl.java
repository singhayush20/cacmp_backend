package com.ayushsingh.cacmp_backend.util.emailUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultEmailServiceImpl implements MailService{

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(AbstractEmailContext email) throws MessagingException {
        log.info("Sending email: " + email);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        try {
            mimeMessageHelper.setFrom(email.getFrom(), email.getFromDisplayName());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }


        mimeMessageHelper.setText(emailContent, true);

        log.info("Sending email " + mimeMessageHelper);

        emailSender.send(message);
    }
}
