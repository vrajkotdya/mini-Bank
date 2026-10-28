package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) {
            return new String[0];
        }
        List<String> errors = new ArrayList<>();
        Class<?> cls = obj.getClass();
        while (cls != null && cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                try {
                    if (f.isAnnotationPresent(Positive.class)) {
                        Object val = f.get(obj);
                        if (val instanceof Number n && n.doubleValue() <= 0) {
                            errors.add(f.getName() + " " + f.getAnnotation(Positive.class).message());
                        }
                    }
                    if (f.isAnnotationPresent(MaxLength.class)) {
                        Object val = f.get(obj);
                        int max = f.getAnnotation(MaxLength.class).value();
                        if (val instanceof String s && s.length() > max) {
                            errors.add(f.getName() + " exceeds max length of " + max);
                        }
                    }
                } catch (IllegalAccessException e) {
                }
            }
            cls = cls.getSuperclass();
        }
        return errors.toArray(new String[0]);
    }
}
