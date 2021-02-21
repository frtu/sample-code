package com.github.frtu.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListTest {
    @Test
    void append() {
        final LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.append(1).append(2);
        assertEquals(2, linkedList.size());
    }

    @Test
    void delete() {
        final LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.append(1).append(2);
        linkedList.delete(3);
        assertEquals(2, linkedList.size());
        linkedList.delete(1);
        assertEquals(1, linkedList.size());
        linkedList.delete(2);
        assertEquals(0, linkedList.size());
    }
}