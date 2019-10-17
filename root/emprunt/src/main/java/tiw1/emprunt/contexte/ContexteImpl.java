package tiw1.emprunt.contexte;

import java.util.HashMap;
import java.util.Map;

public class ContexteImpl implements Contexte {

    private Map<String, Object> references = new HashMap<>();

    @Override
    public Object get(String name) {
        return references.get(name);
    }

    @Override
    public void save(String name, Object object) {
        references.put(name, object);
    }
}
