package org.example.paymentbot.discord;

import lombok.SneakyThrows;
import javax.naming.OperationNotSupportedException;
import java.util.HashMap;
import java.util.Map;

public class LocksMap extends HashMap<String, Object> {
    @Override
    public Object get(Object key) {
        Object result = super.get(key);
        if(result != null) return result;
        result = new Object();
        super.put((String)key,result);
        return result;
    }

    @SneakyThrows
    @Override
    public Object put(String key, Object value) {
        throw new OperationNotSupportedException();
    }

    @SneakyThrows
    @Override
    public void putAll(Map<? extends String, ?> m) {
        throw new OperationNotSupportedException();
    }
}
