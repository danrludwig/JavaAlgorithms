public class PriorityQueue<E> {
    Node<E> root;
    private int size = 0;

    public void insert(E value, int priority) {
        Node<E> newNode = new Node<>(value);
        newNode.adjustPriority(priority);
        if (size == 0) {
            root = newNode;
            size++;
        } else {
            this.root = merge(root, newNode);
            size++;
            }
        }

    private void checkSkewed() {
        checkSkewed(root);
    }

    private void checkSkewed(Node<E> current) {
        if (current == null) {
            return;
        }
        checkSkewed(current.left);
        checkSkewed(current.right);
        if (current.left == null && current.right != null) {
            current.left = current.right;
            current.right = null;
        } else if (current.left != null && current.right != null) {
                if (current.right.distanceToNull > current.left.distanceToNull) {
                    Node<E> temp = current.left;
                    current.left = current.right;
                    current.right = temp;
                }
            }
    }

    private void updateDistance(){
        updateDistance(root);
    }

    private void updateDistance(Node<E> current) {
        if (current == null) {
            return;
        }
        if (current.right == null || current.left == null) {
            current.distanceToNull = 0;
        } else if (current.left.distanceToNull > current.right.distanceToNull) {
            current.distanceToNull = current.right.distanceToNull + 1;
        } else {
            current.distanceToNull = current.left.distanceToNull + 1;
        }
    }


    public Node<E> removeMin() {
        Node<E> temp = root;
        root = merge(root.left, root.right);
        checkSkewed();
        updateDistance();
        return temp;
    }

    public int getSize(){
        return this.size;
    }

    private Node<E> merge(Node<E> e1, Node<E> e2) {
        if (e1 == null && e2 == null) {
            return null;
        }
        if (e1 == null) {
            return e2;
        }
        if (e2 == null) {
            return e1;
        }

        if (e1.priority < e2.priority) {
            e1.right = merge(e1.right, e2);
            updateDistance();
            if (e1.left == null) {
                Node<E> temp = e1.left;
                e1.left = e1.right;
                e1.right = temp;
            }
            return e1;
        } else {
            e2.right = merge(e2.right, e1);
            updateDistance();
            if (e1.left == null) {
                Node<E> temp = e1.left;
                e1.left = e1.right;
                e1.right = temp;
            }
            return e2;
        }

    }

}
