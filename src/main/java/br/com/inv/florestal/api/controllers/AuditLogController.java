package br.com.inv.florestal.api.controllers;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inv.florestal.api.models.audit.AuditLog;
import br.com.inv.florestal.api.models.audit.AuditLog.AuditAction;
import br.com.inv.florestal.api.service.AuditService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditService auditService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAllAuditLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<AuditLog> auditLogs = auditService.getAuditLogs(
            PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"))
        );
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/entity/{entityName}/{entityId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByEntity(
            @PathVariable String entityName,
            @PathVariable String entityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<AuditLog> auditLogs = auditService.getAuditLogsByEntity(
            entityName, 
            entityId, 
            PageRequest.of(page, size)
        );
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<AuditLog> auditLogs = auditService.getAuditLogsByUser(
            userId, 
            PageRequest.of(page, size)
        );
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/action/{action}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByAction(
            @PathVariable AuditAction action,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<AuditLog> auditLogs = auditService.getAuditLogsByAction(
            action, 
            PageRequest.of(page, size)
        );
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<AuditLog>> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<AuditLog> auditLogs = auditService.getAuditLogsByDateRange(
            startDate, 
            endDate, 
            PageRequest.of(page, size)
        );
        return ResponseEntity.ok(auditLogs);
    }
}
