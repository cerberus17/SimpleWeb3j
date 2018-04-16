import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyMultiMap <K, V> implements MultiMap<K, V> {
  private Map<K, Set<V>> mEntries;

  public MyMultiMap() {
    mEntries = new HashMap<>();
  }

  public Iterable<V> get(K key) throws Exception {
    return mEntries.get(key);
  }

  // returns a sequence of values or an empty sequence if the key is not found
  public Iterable<V> getOrDefault(K key) {
    Set<V> values = this.mEntries.get(key);

    if (values == null)
      values = Collections.EMPTY_SET;

    return values;
  }

  // returns true if as a result of the operation the collection gets modified; otherwise false
  public boolean add(K key, V value) {
    Set<V> values = this.mEntries.get(key);

    if (values == null)
      this.mEntries.put(key, (values = new HashSet<V>()));

    return values.add(value);
  }

  // removes the value from the set of values associated with the key
  public void remove(K key, V value) {
    Set<V> values = this.mEntries.get(key);

    if (values != null)
      values.remove(value);
  }

  // removes the key and all the values associated with the key
  public void clear(K key) {
    this.mEntries.remove(key);
  }

  public Iterator<Map.Entry<K,V>> iterator() {
    return this.mEntries.entrySet().stream().flatMap(entry -> entry.getValue().stream().map(value -> (Map.Entry<K, V>) new AbstractMap.SimpleEntry<>(entry.getKey(), value))).iterator();
  }

  public static void main(String[] args) {
    try {
      MyMultiMap<String, Integer> m = new MyMultiMap<>();

      m.add("key1", 123);
      m.add("key2", 1234);
      m.add("key1", 456);

      for (Map.Entry<String, Integer> entry : m) {
        System.out.println("Key=" + entry.getKey() + ";Value=" + entry.getValue());
      }
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }
}