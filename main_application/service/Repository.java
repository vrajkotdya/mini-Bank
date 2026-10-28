package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import model.annotation.Id;

public class Repository<T> {
    private HashMap<String, T> store = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            return null;
        }
        String idValue = null;
        Class<?> cls = entity.getClass();
        while (cls != null && cls != Object.class) {
            for (Field f : cls.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    try {
                        Object val = f.get(entity);
                        if (val != null) {
                            idValue = val.toString();
                        }
                    } catch (IllegalAccessException e) {
                    }
                    break;
                }
            }
            if (idValue != null) {
                break;
            }
            cls = cls.getSuperclass();
        }

        if (idValue != null) {
            store.put(idValue, entity);
        }
        return entity;
    }

    public T findById(String id) {
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public int count() {
        return store.size();
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            if (val != null) {
                sum += val.doubleValue();
            }
        }
        return sum / values.length;
    }
}
