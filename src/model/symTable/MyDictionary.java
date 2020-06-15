package model.symTable;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    HashMap<K, V> dictionary;

    public boolean isDefined(K key) {
        if (dictionary.get(key) != null)
            return true;
        return false;
    }

    public HashMap<K, V> getContainer() {
        return dictionary;
    }

    public void setContainer(HashMap<K, V> dictionary) {
        this.dictionary = dictionary;
    }

    public MyDictionary() {
        this.dictionary = new HashMap<K, V>();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        for (Map.Entry<K, V> entry : dictionary.entrySet()){
            string.append("(").append(entry.getKey()).append(" = ").append(entry.getValue()).append(")").append("\n");
        }

        return string.toString();
    }

    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    public int size() {
        return dictionary.size();
    }

    public V getValue(K key){
        return dictionary.get(key);
    }

    public V put(K key, V val){
        return dictionary.put(key, val);
    }

    public V remove(K key){
        return dictionary.remove(key);
    }
}
