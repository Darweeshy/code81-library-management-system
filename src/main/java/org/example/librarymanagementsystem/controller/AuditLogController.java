package org.example.librarymanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.AuditLogSimpleDto;
import org.example.librarymanagementsystem.model.AuditLog;
import org.example.librarymanagementsystem.repo.AuditLogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuditLogSimpleDto>> getSimpleLogs() {
        List<AuditLog> logs = auditLogRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        List<AuditLogSimpleDto> result = logs.stream()
                .map(log -> new AuditLogSimpleDto(
                        log.getUsername(),
                        log.getRole() != null ? log.getRole().name() : null,
                        log.getAction().name(),
                        log.getDetails(),
                        log.getTimestamp()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
