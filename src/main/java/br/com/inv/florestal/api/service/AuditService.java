package br.com.inv.florestal.api.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.inv.florestal.api.models.audit.AuditLog;
import br.com.inv.florestal.api.models.audit.AuditLog.AuditAction;
import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    public void logAction(
        AuditAction action,
        String entityName,
        String entityId,
        String description
    ) {
        logAction(action, entityName, entityId, description, null, null);
    }

    public void logAction(
        AuditAction action,
        String entityName,
        String entityId,
        String description,
        Object oldValue,
        Object newValue
    ) {
        String ipAddress = null;
        String userAgent = null;
        
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                ipAddress = getClientIpAddress(request);
                userAgent = request.getHeader("User-Agent");
                log.debug("Captured IP: {} and User-Agent: {}", ipAddress, userAgent);
            } else {
                log.debug("No RequestAttributes available");
            }
        } catch (Exception e) {
            log.debug("Could not capture request information for audit log", e);
        }
        
        persistAuditLogAsync(action, entityName, entityId, description, oldValue, newValue, ipAddress, userAgent);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void persistAuditLogAsync(
        AuditAction action,
        String entityName,
        String entityId,
        String description,
        Object oldValue,
        Object newValue,
        String ipAddress,
        String userAgent
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            AuditLog.AuditLogBuilder builder = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .description(description)
                .timestamp(LocalDateTime.now());

            if (authentication != null && authentication.isAuthenticated() 
                && !(authentication.getPrincipal() instanceof String)) {
                User user = (User) authentication.getPrincipal();
                builder.userId(user.getId().toString())
                    .userName(user.fullName())
                    .userEmail(user.getEmail());
            }

            builder.ipAddress(ipAddress);
            builder.userAgent(userAgent);

            if (oldValue != null) {
                builder.oldValue(objectMapper.writeValueAsString(oldValue));
            }

            if (newValue != null) {
                builder.newValue(objectMapper.writeValueAsString(newValue));
            }

            AuditLog auditLog = builder.build();
            auditLogRepository.save(auditLog);
            
            log.info("Audit log created: {} {} on {} with ID {}", 
                action, entityName, entityId, auditLog.getId());
                
        } catch (JsonProcessingException e) {
            log.error("Error serializing audit log values", e);
        } catch (Exception e) {
            log.error("Error creating audit log", e);
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String[] headers = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim();
            }
        }

        return request.getRemoteAddr();
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByEntity(String entityName, String entityId, Pageable pageable) {
        return auditLogRepository.findByEntityNameAndEntityIdOrderByTimestampDesc(
            entityName, entityId, pageable
        );
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByUser(String userId, Pageable pageable) {
        return auditLogRepository.findByUserIdOrderByTimestampDesc(userId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByAction(AuditAction action, Pageable pageable) {
        return auditLogRepository.findByActionOrderByTimestampDesc(action, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByDateRange(
        LocalDateTime startDate, 
        LocalDateTime endDate, 
        Pageable pageable
    ) {
        return auditLogRepository.findByTimestampBetween(startDate, endDate, pageable);
    }

    @Transactional
    public void deleteAuditLog(Long id) {
        auditLogRepository.deleteById(id);
        log.debug("Audit log deleted: {}", id);
    }

    @Transactional
    public void deleteAuditLogs(List<Long> ids) {
        auditLogRepository.deleteAllById(ids);
        log.debug("Audit logs deleted in batch: {} items", ids.size());
    }
}
