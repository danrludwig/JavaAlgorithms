// Author: Dan Ludwig
// CS 2420
// Assignment 1

public class QueueLink<T> {
    private int count;
    private Node<T> firstNode;
    private Node<T> lastNode;

    // Creates nodes for the queue to store the objects
    private class Node<T> {
        private T item;
        private Node<T> next;

        public Node() {
            this.item = null;
            this.next = null;
        }

        public Node(T value) {
            this.item = value;
            this.next = null;
        }

        public Node(T value, Node<T> nextNode) {
            this.item = value;
            this.next = nextNode;
        }
    }

    // Default constructor
    public QueueLink() {
        this.firstNode = null;
        this.lastNode = null;
        this.count = 0;
    }

    // adds the object as a node to the beginning of the queue
    public void enqueue(T value) {
        Node<T> temp = this.lastNode;
        this.lastNode = new Node<T>(value);
        if (isEmpty()) {
            this.firstNode = this.lastNode;
        }
        else {
            temp.next = this.lastNode;
        }
        this.count++;
    }

    // removes the first node on the queue
    public T dequeue() {
        if (isEmpty())
        {
            System.out.println("The queue is empty");
            return null;
        }
        T nodeValue = firstNode.item;
        this.firstNode = firstNode.next;
        this.count--;
        if (isEmpty()) {
            this.firstNode = null;
            this.lastNode = null;
        }
        return nodeValue;
    }

    // returns the object of the front node
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return firstNode.item;
    }

    // returns true if the queue is empty
    public boolean isEmpty() {
        return this.count <= 0;
    }

    // returns size of queue
    public int size() {
        return this.count;
    }

}