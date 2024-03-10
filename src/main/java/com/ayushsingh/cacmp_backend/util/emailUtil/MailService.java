package com.ayushsingh.cacmp_backend.util.emailUtil;

import jakarta.mail.MessagingException;

public interface MailService {

    void sendEmail(final AbstractEmailContext email) throws MessagingException;

}
