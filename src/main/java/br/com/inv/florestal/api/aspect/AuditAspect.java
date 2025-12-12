package br.com.inv.florestal.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.inv.florestal.api.models.user.User;
import br.com.inv.florestal.api.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;

    @Around("@annotation(auditable)")
    public Object auditMethod(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        
        Object oldValue = null;
        Object result = null;
        
        try {

            result = joinPoint.proceed();

            if (auditable.action() == br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.UPDATE ||
                auditable.action() == br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.DELETE) {
                String entityId = extractEntityId(args, null, auditable.action());
                if (!entityId.equals("unknown")) {
                    try {
                        Object target = joinPoint.getTarget();
                        var findByIdMethod = target.getClass().getMethod("findById", Long.class);
                        Object findResult = findByIdMethod.invoke(target, Long.parseLong(entityId));         
                        if (findResult instanceof java.util.Optional) {
                            java.util.Optional<?> optional = (java.util.Optional<?>) findResult;
                            oldValue = optional.orElse(null);
                        } else {
                            oldValue = findResult;
                        }
                    } catch (Exception e) {
                        log.debug("Could not fetch old value for {} audit", auditable.action(), e);
                    }
                }
            }
                        
            String entityId = extractEntityId(args, result, auditable.action());
            String entityName = auditable.entityName().isEmpty() 
                ? extractEntityName(signature.getDeclaringType().getSimpleName())
                : auditable.entityName();
            
            String description = auditable.description().isEmpty()
                ? generateDescription(auditable.action(), entityName)
                : auditable.description();
            
            Object newValue = null;
            switch (auditable.action()) {
                case CREATE:
                    newValue = result;
                    break;
                case UPDATE:
                    newValue = result;
                    break;
                case DELETE:
                    if (result != null) {
                        oldValue = result;
                    }
                    break;
                default:
                    newValue = result;
                    break;
            }
            
            auditService.logAction(
                auditable.action(),
                entityName,
                entityId,
                description,
                oldValue,
                newValue
            );
            
            return result;
            
        } catch (Exception e) {
            log.error("Error during audit logging", e);
            throw e;
        }
    }

    private String extractEntityId(Object[] args, Object result, br.com.inv.florestal.api.models.audit.AuditLog.AuditAction action) {
        if (action == br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.LOGIN) {
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.getPrincipal() instanceof User) {
                    User user = (User) auth.getPrincipal();
                    return user.getId().toString();
                }
            } catch (Exception e) {
                log.debug("Could not extract user ID from authentication context", e);
            }
        }
        
        if (result != null && result instanceof Number) {
            return result.toString();
        }
        
        if (args.length > 0) {
            Object firstArg = args[0];
            if (firstArg instanceof Number) {
                return firstArg.toString();
            }
            
            try {
                var idMethod = firstArg.getClass().getMethod("getId");
                Object id = idMethod.invoke(firstArg);
                if (id != null) {
                    return id.toString();
                }
            } catch (Exception e) {}
        }
        
        return "unknown";
    }

    private String extractEntityName(String serviceName) {
        return serviceName.replace("Service", "");
    }

    private String generateDescription(
        br.com.inv.florestal.api.models.audit.AuditLog.AuditAction action, 
        String entityName
    ) {
        return switch (action) {
            case CREATE -> entityName + " criado";
            case UPDATE -> entityName + " atualizado";
            case DELETE -> entityName + " excluído";
            case STATUS_CHANGE -> "Status do " + entityName + " alterado";
            case LOCK_CHANGE -> "Bloqueio do " + entityName + " alterado";
            case PASSWORD_CHANGE -> "Senha do " + entityName + " alterada";
            default -> action.name() + " em " + entityName;
        };
    }
}
