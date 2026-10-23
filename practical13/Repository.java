package practical13;

import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T> {
    private HashMap<String, T> map = new HashMap<>();

    public void save(String id, T item) {
        map.put(id, item);
    }

    public T findById(String id) {
        return map.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(map.values());
    }
}
