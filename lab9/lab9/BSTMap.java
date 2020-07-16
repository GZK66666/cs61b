package lab9;

import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements lab9.Map61B<K, V> {
    private int size;
    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        public Node(K k, V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

    private class BSTMapIter implements Iterator<K> {
        private Stack<Node> stack = new Stack<>();

        public BSTMapIter(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node current = stack.pop();

            if (current.right != null) {
                Node node = current.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }

            return current.key;
        }
    }

    public BSTMap() {
        size = 0;
        root = null;
    }

    @Override
    public void clear() {
        //清空：将根结点置为空，并将size设置为0，代表当前树中没有结点
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return get(key) != null;
    }

    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }

        if (node.key.compareTo(key) == 0) {
            return node.value;
        }else if (node.key.compareTo(key) > 0) {
            return get(node.left, key);
        }else {
            return get(node.right, key);
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        return get(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            size += 1;
            return new Node(key, value);
        }

        if (node.key.compareTo(key) < 0) {
            node.right = put(node.right, key, value);
        }else if (node.key.compareTo(key) > 0) {
            node.left = put(node.left, key, value);
        }else {
            node.value = value;
        }

        return node;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        root = put(root, key, value);
    }

    private void preOrderTranverse(Set<K> set, Node node) {
        if (node == null) {
            return;
        }

        preOrderTranverse(set, node.left);
        set.add(node.key);
        preOrderTranverse(set, node.right);
    }

    @Override
    public Set keySet() {
        if (root == null) {
            return null;
        }

        Set<K> set = new HashSet<>();

        preOrderTranverse(set, root);

        return set;
    }

    private Node swapSmallest(Node T, Node R) {
        if (T.left == null) {
            R.key = T.key;
            return T.right;
        }else {
            T.left = swapSmallest(T.left, R);
            return T;
        }
    }

    private Node removeRevursive(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (node.key.compareTo(key) > 0) {
            node.left = removeRevursive(node.left, key);
        }
        else if (node.key.compareTo(key) < 0) {
            node.right = removeRevursive(node.right, key);
        }
        //找到了
        else if (node.left == null) {
            return node.right;
        }else if (node.right == null) {
            return node.left;
        }else {
            node.right = swapSmallest(node.right, node);
        }

        return node;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            root = removeRevursive(root, key);
            size -= 1;
        }

        return value;
    }



    @Override
    public V remove(K key, V value) {
        V value1 = get(key);
        if (value.equals(value1)) {
            remove(key);
            size -= 1;
            return value;
        }else {
            return null;
        }
    }

    @Override
    public Iterator iterator() {
        return new BSTMapIter(root);
    }

    private void printInPreOrder(Node node) {
        if (node == null) {
            return;
        }

        printInPreOrder(node.left);
        System.out.println(node.value);
        printInPreOrder(node.right);
    }

    public void printInOrder() {
        if (root == null) {
            return;
        }

        printInPreOrder(root);
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstMap = new BSTMap<>();
        for (int i = 0; i < 10; i++) {
            bstMap.put("hi" + i, 1 + i);
        }
//        bstMap.printInOrder();
        Iterator<String> itr = bstMap.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

}
