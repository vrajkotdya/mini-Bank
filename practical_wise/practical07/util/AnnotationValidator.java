package practical07.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import practical07.model.annotation.MaxLength;
import practical07.model.annotation.Positive;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) return new String[0];
        List<String> list = new ArrayList<>();
        Class<?> cls = obj.getClass();

        while (cls != null && cls != Object.class) {
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                try {
                    if (f.isAnnotationPresent(Positive.class)) {
                        Object v = f.get(obj);
                        if (v instanceof Number n && n.doubleValue() <= 0) {
                            list.add(f.getName() + " " + f.getAnnotation(Positive.class).message());
                        }
                    }
                    if (f.isAnnotationPresent(MaxLength.class)) {
                        Object v = f.get(obj);
                        int max = f.getAnnotation(MaxLength.class).value();
                        if (v instanceof String s && s.length() > max) {
                            list.add(f.getName() + " length exceeds maximum of " + max);
                        }
                    }
                } catch (IllegalAccessException e) {
                }
            }
            cls = cls.getSuperclass();
        }
        return list.toArray(new String[0]);
    }
}