package model.heap;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    HashMap<K, V> heap;

    public MyHeap() {
        this.heap = new HashMap<K, V>();
    }

    public MyHeap(HashMap h){
        this.heap = h;
    }

    public HashMap<K, V> getContainer() {
        return heap;
    }

    public void setContainer(HashMap<K, V> heap) {
        this.heap = heap;
    }

    public void replace(K key, V val){
        heap.replace(key, val);
    }

    @Override
    public void newAlloc(K key, V val) {
        heap.put(key, val);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (Map.Entry<K, V> entry : heap.entrySet()){
            string.append("(").append(entry.getKey()).append(" -> ").append(entry.getValue()).append(")").append("\n");
        }

        return string.toString();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public V getValue(K key){
        return heap.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return heap.containsKey(key);
    }

}
