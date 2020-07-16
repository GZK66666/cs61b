package lab9;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

public class MyHashMap<K, V> implements lab9.Map61B<K, V> {
    private int bucketSize;
    private double loadFactor;
    private int size;
    private ArrayList<Entry<K, V>>[] data;

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K k, V v) {
            key = k;
            value = v;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V v) {
            value = v;

            return value;
        }
    }

    public MyHashMap() {
        bucketSize = 16;
        loadFactor = 0.75;
        data = new ArrayList[bucketSize];
//        data = (ArrayList<Entry<K, V>>[]) new Object[bucketSize];
        size = 0;
    }

        public MyHashMap(int initialSize) {
        bucketSize = initialSize;
        loadFactor = 0.75;
        data = new ArrayList[bucketSize];
//        data = (ArrayList<Entry<K, V>>[]) new Object[bucketSize];
        size = 0;
    }

    public MyHashMap(int initialSize, double loadFactor) {
        bucketSize = initialSize;
        this.loadFactor = loadFactor;
        data = new ArrayList[bucketSize];
//        data = (ArrayList<Entry<K, V>>[]) new Object[bucketSize];
        size = 0;
    }

    @Override
    public void clear() {
       for (int i = 0; i < bucketSize; i++) {
           data[i] = null;
       }
       size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        ArrayList<Entry<K, V>> list = data[(key.hashCode() & 0x7FFFFFFF) % bucketSize];

        if (list == null) {
            return false;
        }

        for (Entry<K, V> item : list) {
            if (item.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            ArrayList<Entry<K, V>> list = data[(key.hashCode() & 0x7FFFFFFF) % bucketSize];
            for (Entry<K, V> item : list) {
                if (item.getKey().equals(key)) {
                    return item.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        ArrayList<Entry<K, V>>[] resizeData = new ArrayList[bucketSize * 2];

        for (ArrayList<Entry<K, V>> list : data) {
            if (list != null) {
                for (Entry<K, V> item : list) {
                    K key = item.getKey();
                    V value = item.getValue();

                    if (resizeData[(key.hashCode() & 0x7FFFFFFF) % (bucketSize * 2)] == null) {
                        resizeData[(key.hashCode() & 0x7FFFFFFF) % (bucketSize * 2)] = new ArrayList<>();
                    }
                    resizeData[(key.hashCode() & 0x7FFFFFFF) % (bucketSize * 2)].add(new Entry<>(key, value));
                }
            }
        }

        bucketSize *= 2;
        data = resizeData;
    }

    @Override
    public void put(K key, V value) {
        ArrayList<Entry<K, V>> list = data[(key.hashCode() & 0x7FFFFFFF) % bucketSize];

        if (list == null) {
            list = new ArrayList<>();
            list.add(new Entry(key, value));
            data[(key.hashCode() & 0x7FFFFFFF) % bucketSize] = list;
            size += 1;
            if ((double) size / bucketSize > loadFactor) {
                resize();
            }
            return;
        }else {
            for (Entry<K, V> item : list) {
                if (item.getKey().equals(key)) {
                    item.setValue(value);
                    return;
                }
            }
            list.add(new Entry<>(key, value));
            size += 1;
            if ((double) size / bucketSize > loadFactor) {
                resize();
            }
        }
    }

    @Override
    public Set keySet() {
        Set<K> sets = new HashSet<>();

        for (ArrayList<Entry<K, V>> list : data) {
            if (list != null) {
                for (Entry<K, V> item : list) {
                    sets.add(item.getKey());
                }
            }
        }

        return sets;
    }

    @Override
    public V remove(K key) {
        V value = null;

        if (!containsKey(key)) {
            return null;
        }

        ArrayList<Entry<K, V>> list = data[(key.hashCode() & 0x7FFFFFFF) % bucketSize];
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                value = list.get(i).getValue();
                list.remove(i);
                break;
            }
        }

        return value;
    }

    @Override
    public V remove(K key, V value) {
        if (!containsKey(key)) {
            return null;
        }

        ArrayList<Entry<K, V>> list = data[(key.hashCode() & 0x7FFFFFFF) % bucketSize];
        for (int i = 0; i < list.size(); i++) {
            K k = list.get(i).getKey();
            V v = list.get(i).getValue();
            if (k.equals(key) && v.equals(value)) {
                list.remove(i);
                break;
            }
        }

        return value;
    }

    @Override
    public Iterator iterator() {
        return keySet().iterator();
    }
}
