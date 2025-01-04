package com.cruru.email.service;

import com.cruru.applicant.domain.Applicant;
import com.cruru.club.domain.Club;
import com.cruru.email.domain.Email;
import com.cruru.email.domain.repository.EmailRepository;
import com.cruru.email.util.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    @Async
    public CompletableFuture<Email> send(
            Club from, Applicant to, String subject, String content, List<File> tempFiles) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to.getEmail());
            helper.setSubject(EmailTemplate.defaultEmailSubject(from.getName(), subject));
            helper.setText(EmailTemplate.generateDefaultEmailTemplate(from.getName(), subject, content), true);
            if (hasFile(tempFiles)) {
                addAttachments(helper, tempFiles);
            }
            mailSender.send(message);

            log.info("이메일 전송 성공: from={}, to={}, subject={}", from.getId(), to.getEmail(), subject);
            return CompletableFuture.completedFuture(new Email(from, to, subject, content, true));
        } catch (MessagingException | MailException e) {
            log.info("이메일 전송 실패: from={}, to={}, subject={}", from.getId(), to.getEmail(), e.getMessage());
            return CompletableFuture.completedFuture(new Email(from, to, subject, content, false));
        }
    }

    private boolean hasFile(List<File> files) {
        return files != null && !files.isEmpty();
    }

    private void addAttachments(MimeMessageHelper helper, List<File> files) throws MessagingException {
        for (File file : files) {
            addAttachment(helper, file);
        }
    }

    private void addAttachment(MimeMessageHelper helper, File file) throws MessagingException {
        String fileName = file.getName();
        if (isValidateFileName(fileName)) {
            helper.addAttachment(fileName, file);
        }
    }

    private boolean isValidateFileName(String fileName) {
        return fileName != null && !fileName.isEmpty();
    }

    @Transactional
    public void save(Email email) {
        emailRepository.save(email);
    }

    @Transactional
    public void deleteAllByTos(List<Applicant> applicants) {
        emailRepository.deleteAllByTos(applicants);
    }

    @Async
    public void sendVerificationCode(String to, String verificationCode) {
        try {
            String subject = "[크루루] 인증 코드 안내";
            String content = EmailTemplate.generateVerificationEmailContent(verificationCode);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            log.error("이메일 전송 실패: to={}, subject={}", to, e.getMessage());
        }
    }

    public List<Email> findAllByFromAndTo(Club from, Applicant to) {
        return emailRepository.findAllByFromAndTo(from, to);
    }
}
