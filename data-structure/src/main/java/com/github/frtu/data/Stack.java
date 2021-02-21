package com.github.frtu.data;

public class Stack<T> {
    public static class EmptyStackException extends IllegalStateException {
    }

    public static class StackNode<T> {
        private T value;
        private StackNode next;

        public StackNode(T value) {
            this.value = value;
        }
    }

    private StackNode<T> top;

    public void push(T value) {
        final StackNode stackNode = new StackNode(value);
        stackNode.next = top;
        top = stackNode;
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();

        T result = top.value;
        top = top.next;
        return result;
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        
        return top.value;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
