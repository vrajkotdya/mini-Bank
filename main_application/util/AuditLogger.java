package util;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import model.annotation.Audit;
import service.TransactionLog;

public class AuditLogger {
    private static final Path AUDIT_LOG = Paths.get("logs", "audit.log");

    public static void logIfAnnotated(Object target, String methodName, Object... args) {
        if (target == null) return;
        try {
            for (Method m : target.getClass().getMethods()) {
                if (m.getName().equals(methodName) && m.isAnnotationPresent(Audit.class)) {
                    Audit a = m.getAnnotation(Audit.class);
                    String action = a.action().isEmpty() ? methodName : a.action();
                    String line = LocalDateTime.now() + " | AUDIT | " + action;
                    TransactionLog.append(AUDIT_LOG, line);
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
}
