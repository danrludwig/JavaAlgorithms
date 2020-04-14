public class Node<E> {
    public E element;
    public Node<E> right;
    public Node<E> left;
    public Node<E> parent;
    public int priority;
    public int distanceToNull;

    Node(E value) {
        this.element = value;
        this.right = null;
        this.left = null;
        this.priority = 0;
        this.parent = null;
    }

    public void adjustPriority(int priority) {
        this.priority = priority;
    }
}
