package org.example.librarymanagementsystem.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.librarymanagementsystem.model.AuditLog;
import org.example.librarymanagementsystem.repo.AuditLogRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent successEvent) {
            String username = successEvent.getAuthentication().getName();
            auditLogRepository.save(AuditLog.builder()
                    .username(username)
                    .action("LOGIN_SUCCESS")
                    .timestamp(LocalDateTime.now())
                    .details("Authenticated successfully")
                    .build());
        }

        if (event instanceof AuthenticationFailureBadCredentialsEvent failureEvent) {
            String username = failureEvent.getAuthentication().getName();
            auditLogRepository.save(AuditLog.builder()
                    .username(username)
                    .action("LOGIN_FAILED")
                    .timestamp(LocalDateTime.now())
                    .details("Bad credentials")
                    .build());
        }
    }
}
