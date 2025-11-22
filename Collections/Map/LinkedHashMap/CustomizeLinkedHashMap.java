import java.util.LinkedHashMap;
import java.util.Map;

public class CustomizeLinkedHashMap extends LinkedHashMap<Integer, String> {
    private static final int MAX_ENTRIES = 3;

    public CustomizeLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > MAX_ENTRIES;
    }
}
