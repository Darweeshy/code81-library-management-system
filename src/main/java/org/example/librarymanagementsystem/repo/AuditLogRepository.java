package org.example.librarymanagementsystem.repo;

import org.example.librarymanagementsystem.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
