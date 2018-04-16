/*

Implement the following interface.
It allows to store a set of unique values associated with a single key.

Example:
var dict = new MultiMapImpl<String, Integer>();
dict.add("key1", 123);
dict.add("key1", 234);

// at this point it contains 2 values (123, 234) associated with "key1"

*/


import java.util.*;

interface MultiMap<K,V> extends Iterable<Map.Entry<K,V>> {

  // returns a sequence of values for the key or throws exception if the key is not found
  public Iterable<V> get(K key) throws Exception;

  // returns a sequence of values or an empty sequence if the key is not found
  public Iterable<V> getOrDefault(K key);

  // returns true if as a result of the operation the collection gets modified; otherwise false
  public boolean add(K key, V value);

  // removes the value from the set of values associated with the key
  public void remove(K key, V value);

  // removes the key and all the values associated with the key
  public void clear(K key);
}



