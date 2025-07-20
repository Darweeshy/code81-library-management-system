package org.example.librarymanagementsystem.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEventListener
        implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (event instanceof AuthenticationSuccessEvent success) {
            log.info("✅ Login success for user: {}", success.getAuthentication().getName());
        } else if (event instanceof AbstractAuthenticationFailureEvent failure) {
            log.warn("❌ Login failed: {}", failure.getException().getMessage());
        }
    }
}
