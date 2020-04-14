// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree
    private int count = 0;

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create non ordered tree from list in preorder
     * @param arr    List of elements
     * @param label  Name of tree
     * @param height Maximum height of tree
     */
    public Tree(ArrayList<E> arr, String label, int height) {
        this.treeName = label;
        root = buildTree(arr, height, null);
    }

    /**
     * Create BST
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.size(); i++) {
            bstInsert(arr.get(i));
        }
    }


    /**
     * Create BST from Array
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    public String toString() {
        if (root == null)
            return (treeName + " Empty tree\n");
        return treeName + "\n" + recursiveToString(root);
    }

    // Complexity: O(n^(log2))
    public String recursiveToString(BinaryNode<E> node) {
        // If the node is null there is nothing to return, so return nothing
        if (node == null) {
            return "";
        }
        // This keeps track of the parent node and makes sure there isn't any error by
        // trying to return an element of null
        BinaryNode<E> theParent = node.parent;
        String parentNode;
        if (theParent != null) {
             parentNode = "" + theParent.element;
        } else {
            parentNode = "no parent";
        }

        // Recursively checks each node and returns the string in the form of a string
        // also adds the parent node in brackets
        return recursiveToString(node.left) + " " + node.element + " [" + parentNode + "] \n" +
                recursiveToString(node.right);
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
         root = flip(root);
    }

    // Complexity: O(n^(log2))
    public BinaryNode<E> flip(BinaryNode<E> node) {
        if (node == null) {
            return node;
        }
        // Recursive method goes through each of the nodes and finds the nodes
        // at the same point of the tree
        BinaryNode<E> left = flip(node.left);
        BinaryNode<E> right = flip(node.right);
        // Flips the left and right side of the nodes, flipping the tree one spot at a time
        node.left = right;
        node.right = left;
        // Returns the node and then moves to the next one until completed
        return node;
    }

    /**
     * Find successor of "curr" node in tree
     * @return String representation of the successor
     * Complexity: O(logn)
     */
    public String successor() {
        if (curr == null) curr = root;
        // Checks to see if the successor is going to be the child of the current node
        // if so a recursive method is called to find the successor
        if (curr.right != null) {
            curr = successor(curr.right);
        } else {
            //If the right child is null then it goes up the chain until it finds a parent with a left node
            //
            curr = curr.parent;
            while (curr.left == null) {
                curr = curr.parent;
            }
        }
        if (curr == null) return "null";
        else return curr.toString();
    }

    private BinaryNode<E> successor(BinaryNode<E> curr) {
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    /**
     * Counts number of nodes in specifed level
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     * O(n)
     */
    public int nodesInLevel(int level) {
        if (level == 0) {
            return 1;
        }
        return nodesInLevel(root, level);
    }

    private int nodesInLevel(BinaryNode<E> node, int level) {
        // Checks to make sure it isn't a leaf node
        if (node.left == null && node.right == null && level > 0) {
            return 0;
        }
        // If both left and right aren't null it adds both sides
        if (node.left != null && node.right != null && level > 0) {
            return nodesInLevel(node.left, level - 1) + nodesInLevel(node.right, level - 1);
        }
        // adds just right
        if (node.left == null && node.right != null && level > 0) {
            return nodesInLevel(node.right, level - 1);
        }
        // adds just left
        if (node.left != null && node.right == null && level > 0) {
            return nodesInLevel(node.left, level - 1);
        }
        return 1;
    }

    /**
     * Print all paths from root to leaves
     * Complexity: O(n^(log2))
     */
    public void printAllPaths() {
        // Checks to see if the tree is empty, if so it tells us.
        if (root == null) {
            System.out.println(treeName + " Empty tree\n");
            return;
        }
        // Creates a string for us to append then starts the recursive journey
        String sequence = "";
        printAllPaths(root, sequence);
    }

    private void printAllPaths(BinaryNode<E> node, String sequence) {
        // Makes sure the node isn't null
        if(node == null) {
            return;
        }
        // Adds the element of the node to the string, do this for every node to get every element in the path
        sequence += node.element + " ";
        // When it gets to the end of the path, prints the path sequence
        if (node.left == null && node.right == null) {
            System.out.println(sequence );
            return;
        }
        // moves through all nodes
        printAllPaths(node.left, sequence);
        printAllPaths(node.right, sequence);
    }

    /**
     * Print contents of levels in zig zag order starting at maxLevel
     * @param maxLevel
     * Big(O) notation: N Log N
     */
    public void byLevelZigZag(int maxLevel) {
        if (maxLevel == 0) {
            System.out.println(root);
        }

        for (int i=maxLevel; i>=1; i--) {
            byLevelZigZag(i, root);
        }
        System.out.println();
    }

    public void byLevelZigZag(int depth, BinaryNode<E> node) {
        if (node == null) {
            return;
        }
        // if it found the correct depth then print out the node
        if (depth == 1) {
            System.out.print(node.element + " ");
        }
        // prints in a certain order from left to right or right to left depending on the depth
        if (depth % 2 == 0) {
            byLevelZigZag(depth - 1, node.right);
            byLevelZigZag(depth - 1, node.left);
        } else {
            byLevelZigZag(depth - 1, node.left);
            byLevelZigZag(depth - 1, node.right);
        }
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     * @return Count of embedded binary search trees
     * Complexity: O(n^(log2))
     */
    public Integer countBST() {
        if (root == null) return 0;
        int count = 0;
        return countBST(root, count);
    }

    private Integer countBST(BinaryNode<E> node, int count) {
        // Makes sure the nodes both have children
        if (node.left == null || node.right == null) {
            return 0;
        }
        // If the node on left child is smaller than the node and
        // makes sure the right child is larger than the node
        if ((Integer)(node.left.element) < (Integer)(node.element) &&
                (Integer)(node.right.element) > (Integer)(node.element)) {
            count += 1;
        }
        // runs through each node
        count += countBST(node.left, count);
        count += countBST(node.right, count);

        return count;
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }

    /**
     * Remove all paths from tree that sum to less than given value
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum) {
        if (sum <= 0) {
            root = null;
        }
        root = pruneK(root, sum);
    }

    private BinaryNode<E> pruneK(BinaryNode<E> node, Integer sum) {
        if (node == null) {
            return null;
        }
        // grabs left and right child of the node and takes the element away from the sum
        node.left = pruneK(node.left, sum - (Integer)(node.element));
        node.right = pruneK(node.right, sum - (Integer)(node.element));

        // when it gets to the end and the sum is more than 0 at the end of the road it removes the node
        // runs through every possible path
        if (node.left == null && node.right == null) {
            if (sum > (Integer)(node.element)) {
                node = null;
            }
        }
        return node;
    }



    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        // Makes sure the tree is null
        root = null;
        root = buildTreeTraversals(inOrder, preOrder, 0, preOrder.length - 1, null);
    }


    // Complexity: n log(n)
    private BinaryNode<E> buildTreeTraversals(E[] inOrder, E[] preOrder, int start, int end, BinaryNode<E> nodeParent) {
        // Make sure not too go too far and to stay in bounds of the list
        if (start > end) {
            return null;
        }
        if (count > preOrder.length - 1) {
            return null;
        }

        // Sets the variable as node from the list and sets the parent node to the previously used node
        BinaryNode node = new BinaryNode<E>(preOrder[this.count]);
        node.parent = nodeParent;
        this.count++;
        if (start == end) {
            return node;
        }
        // Finds the position of the node used in the list
        int index = 0;
        for (int i=start; i <= end; i++) {
            if (inOrder[i] == node.element) {
                index = i;
            }
        }

        // Sets the children nodes
        node.left = buildTreeTraversals(inOrder, preOrder, start, index - 1, node);
        node.right = buildTreeTraversals(inOrder, preOrder, index + 1, end, node);

        return node;
    }

    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public String lca(E a, E b) {
        BinaryNode<E> ancestor = null;
        // Sees which number is smaller and sets that as the lower parameter
        // then starts the recursive method
        if (a.compareTo(b) < 0) {
            ancestor = lca(root, (Integer)(a), (Integer)(b));
        } else {
            ancestor = lca(root, (Integer)(b), (Integer)(a));
        }
        if (ancestor == null) return "none";
        else return ancestor.toString();
    }

    private BinaryNode<E> lca(BinaryNode<E> node, int a, int b) {
        // Checks each node to find the lowest node that is above both the nodes
        // if it can't find it, keep moving up the ladder
        if (node == null) {
            return null;
        }
        if ((Integer)(node.element) > a && (Integer)(node.element) > b) {
            return lca(node.left, a, b);
        }
        if ((Integer)(node.element) < a && (Integer)(node.element) < b) {
            return lca(node.right, a, b);
        }
        return node;
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        root = balanceTree(root);
    }

    private BinaryNode<E> balanceTree(BinaryNode<E> node) {
        if (node == null) {
            return null;
        }

        // Creates a new list, switches the middle node with the first node and
        // builds the tree starting with the first of the list
        ArrayList<E> list = new ArrayList<>();
        ArrayList<E> inOrderList = storeItemsArray(root, list);
        Integer temp = (Integer) inOrderList.get(0);
        inOrderList.remove(0);
        inOrderList.add(0, inOrderList.get(inOrderList.size() / 2));
        inOrderList.remove((inOrderList.size() / 2) + 1);
        inOrderList.add((E) temp);

        Tree<Integer> newTree = new Tree<Integer>((ArrayList<Integer>) inOrderList, "Tree2:");

        return (BinaryNode<E>) newTree.root;
    }

    // Goes through the tree and stores everything in a sorted list by going through it inorder
    private ArrayList<E> storeItemsArray(BinaryNode<E> node, ArrayList<E> list) {
        if (node == null) {
            return list;
        }
        storeItemsArray(node.left, list);
        list.add(node.element);
        storeItemsArray(node.right, list);
        return list;
    }

    /**
     * In a BST, keep only nodes between range
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(E a, E b) {
        if (root == null) {
            return;
        }
        keepRange(root, (Integer)(a), (Integer)(b));
        keepRange(root, (Integer)(a), (Integer)(b));
     }

     private void keepRange(BinaryNode<E> node, int a, int b) {
        // if the node is outside the range then removes the node
        if (node == null) {
            return;
        }
        if ((Integer)(node.element) > b || (Integer)(node.element) < a) {
            remove(node.element);
        }
        keepRange(node.left, a, b);
        keepRange(node.right, a, b);
     }

     private void remove(E value) {
        // removes the node, code was the same code I used for a previous assignment (my own code)
        BinaryNode<E> parent = null;
        BinaryNode<E> current = root;

        boolean finished = false;
        while (!finished) {
            if (value.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (value.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                finished = true;
            }
        }

        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else {
                if (value.compareTo(parent.element) < 0) {
                    parent.left = current.right;
                } else {
                    parent.right = current.right;
                }
            }
        } else {
            BinaryNode<E> parentOfRightMost = current;
            BinaryNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }

            current.element = rightMost.element;

            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else {
                parentOfRightMost.left = rightMost.left;
            }
        }
     }

    //PRIVATE

     /**
     * Build a NON BST tree by preorder
     *
     * @param arr    nodes to be added
     * @param height maximum height of tree
     * @param parent parent of subtree to be created
     * @return new tree
     */
    private BinaryNode<E> buildTree(ArrayList<E> arr, int height, BinaryNode<E> parent) {
        if (arr.isEmpty()) return null;
        BinaryNode<E> curr = new BinaryNode<>(arr.remove(0), null, null, parent);
        if (height > 0) {
            curr.left = buildTree(arr, height - 1, curr);
            curr.right = buildTree(arr, height - 1, curr);
        }
        return curr;
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }



    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; //  Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.element);
                sb.append(">");
            }

            return sb.toString();
        }

    }


    // Test program
    public static void main(String[] args) {
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        final String ENDLINE = "\n";

        int val = 60;
        final int SIZE = 8;

        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9};
        ArrayList<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
        Integer[] v3 = {200, 15, 3, 65, 83, 70, 90};
        ArrayList<Integer> v4 = new ArrayList<Integer>(Arrays.asList(v3));
        Integer[] v = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        ArrayList<Integer> v5 = new ArrayList<Integer>(Arrays.asList(v));
        Integer[] inorder = {4, 2, 1, 7, 5, 8, 3, 6};
        Integer[] preorder = {1, 2, 4, 3, 5, 7, 8, 6};


        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        Tree<Integer> tree2 = new Tree<Integer>(v2, "Tree2:");
        Tree<Integer> tree3 = new Tree<Integer>(v3, "Tree3:");
        Tree<Integer> treeA = new Tree<Integer>(v4, "TreeA:", 2);
        Tree<Integer> treeB = new Tree<Integer>(v5, "TreeB", 3);
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());

        System.out.println(treeA.toString());

        treeA.flip();
        System.out.println("Now flipped" + treeA.toString());

        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree6 class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor());
        }

        System.out.println(tree1.toString());
        for (int mylevel = 0; mylevel < SIZE; mylevel += 2) {
            System.out.println("Number nodes at level " + mylevel + " is " + tree1.nodesInLevel(mylevel));
        }
        System.out.println("All paths from tree1");
        tree1.printAllPaths();

        System.out.print("Tree1 byLevelZigZag: ");
        tree1.byLevelZigZag(5);
        System.out.print("Tree2 byLevelZigZag (3): ");
        tree2.byLevelZigZag(3);
        treeA.flip();
        System.out.println(treeA.toString());
        System.out.println("treeA Contains BST: " + treeA.countBST());

        System.out.println(treeB.toString());
        System.out.println("treeB Contains BST: " + treeB.countBST());

        treeB.pruneK(60);
        treeB.changeName("treeB after pruning 60");
        System.out.println(treeB.toString());
        treeA.pruneK(220);
        treeA.changeName("treeA after pruning 220");
        System.out.println(treeA.toString());

        treeC.buildTreeTraversals(inorder, preorder);
        treeC.changeName("Tree C built from inorder and preorder traversals");
        System.out.println(treeC.toString());

        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(56, 61) + ENDLINE);

        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(6, 25) + ENDLINE);
        System.out.println(tree3.toString());
        tree3.balanceTree();
        tree3.changeName("tree3 after balancing");
        System.out.println(tree3.toString());

        System.out.println(tree1.toString());
        tree1.keepRange(10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());
        tree3.keepRange(3, 85);
        tree3.changeName("tree3 after keeping only nodes between 3  and 85");
        System.out.println(tree3.toString());


    }

}
