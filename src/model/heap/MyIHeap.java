package model.heap;

import java.util.HashMap;

public interface MyIHeap<K, V> {
    boolean isEmpty();
    V getValue(K key);
    int size();
    void replace(K key, V val);
    void newAlloc(K key, V val);
    boolean isDefined(K key);
    HashMap<K, V> getContainer();
    void setContainer(HashMap<K, V> heap);
}
