package model.symTable;

import java.util.HashMap;

public interface MyIDictionary<K, V> {
    boolean isEmpty();
    V getValue(K key);
    V remove(K key);
    int size();
    V put(K key, V val);
    boolean isDefined(K key);
    HashMap<K, V> getContainer();
    void setContainer(HashMap<K, V> heap);
}
