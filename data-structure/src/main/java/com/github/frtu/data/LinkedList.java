package com.github.frtu.data;

public class LinkedList<T> {
    public class Node<T> {
        private Node<T> next;
        private T value;

        public Node(T value) {
            this.value = value;
        }

        public void append(T value) {
            final Node tail = new Node(value);
            Node current = this;
            while (current.next != null) {
                current = current.next;
            }
            current.next = tail;
        }

        public Node remove(Node head, T value) {
            if (head.value == value) {
                return head.next;
            }
            Node current = head.next;
            while (current != null) {
                if (current.value != null && current.value.equals(value)) {
                    current.next = current.next.next;
                    return head;
                }
                current = current.next;
            }
            return head;
        }
    }

    /**
     * Initial empty node
     */
    private Node<T> head;

    public LinkedList<T> append(T value) {
        if (head == null) {
            head = new Node<>(value);
        } else {
            head.append(value);
        }
        return this;
    }

    public LinkedList<T> delete(T value) {
        if (head != null) {
            head = head.remove(head, value);
        }
        return this;
    }

    public int size() {
        int size = 0;
        Node node = head;
        while (node != null) {
            size++;
            node = node.next;
        }
        return size;
    }
}
