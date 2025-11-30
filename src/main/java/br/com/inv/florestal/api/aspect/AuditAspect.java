package br.com.inv.florestal.api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

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
            // Executa o método original
            result = joinPoint.proceed();
            
            // Determina o ID da entidade
            String entityId = extractEntityId(args, result);
            String entityName = auditable.entityName().isEmpty() 
                ? extractEntityName(signature.getDeclaringType().getSimpleName())
                : auditable.entityName();
            
            String description = auditable.description().isEmpty()
                ? generateDescription(auditable.action(), entityName)
                : auditable.description();
            
            // Para CREATE, o valor novo é o resultado (DTO/Representation)
            // Para UPDATE/DELETE, não registra valores detalhados (apenas descrição)
            Object newValue = null;
            if (auditable.action() == br.com.inv.florestal.api.models.audit.AuditLog.AuditAction.CREATE) {
                newValue = result;
            }
            
            // Registra a auditoria
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

    private String extractEntityId(Object[] args, Object result) {
        if (result != null && result instanceof Number) {
            return result.toString();
        }
        
        if (args.length > 0) {
            Object firstArg = args[0];
            if (firstArg instanceof Number) {
                return firstArg.toString();
            }
            
            // Tenta extrair ID via reflection
            try {
                var idMethod = firstArg.getClass().getMethod("getId");
                Object id = idMethod.invoke(firstArg);
                if (id != null) {
                    return id.toString();
                }
            } catch (Exception e) {
                // Ignora se não encontrar método getId
            }
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
