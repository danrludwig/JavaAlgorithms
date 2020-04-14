// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree and set the root to null.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param item the item to insert.
     */
    public void insert( AnyType item )
    {
        root = insert( item, root );
    }


    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param item the item to remove.
     */
    public void remove( AnyType item )
    {
        root = remove( item, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param item the item to remove.
     * @param curr the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType item, AvlNode<AnyType> curr ) {
        if( curr == null )
            return curr;   // Item not found; do nothing

        int compareResult = item.compareTo( curr.element );

        if( compareResult < 0 )
            curr.left = remove( item, curr.left );
        else if( compareResult > 0 )
            curr.right = remove( item, curr.right );
        else if( curr.left != null && curr.right != null ) // Two children
        {
            curr.element = findMin( curr.right ).element;
            curr.right = remove( curr.element, curr.right );
        }
        else
            curr = ( curr.left != null ) ? curr.left : curr.right;
        return balance( curr );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( ) {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }

    public  void  deleteMin( ) {
        AvlNode<AnyType> curr = traverseToLowestNode(root);
        AvlNode<AnyType> currParent = lowestNodeParent(root);

        if(curr.right != null) {
            if (currParent == null) {
                root.left = curr.right;
            } else {
                currParent.left = curr.right;
            }
        } else if (currParent != null) {
            currParent.left = null;
        }
        root = balance(root);
     }

     private AvlNode<AnyType> lowestNodeParent(AvlNode<AnyType> curr) {
        if (curr.left.left == null) {
            return null;
        }
        while (curr.left.left != null) {
            curr = curr.left;
        }
        return curr;
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( ) {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param item the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType item )
    {
        return contains( item, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label) {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> curr ) {
        if( curr == null )
            return curr;

        if( height( curr.left ) - height( curr.right ) > ALLOWED_IMBALANCE )
            if( height( curr.left.left ) >= height( curr.left.right ) )
                curr = rightRotation( curr );
            else
                curr = doubleRightRotation( curr );
        else
        if( height( curr.right ) - height( curr.left ) > ALLOWED_IMBALANCE )
            if( height( curr.right.right ) >= height( curr.right.left ) )
                curr = leftRotation( curr );
            else
                curr = doubleLeftRotation( curr );

        curr.height = Math.max( height( curr.left ), height( curr.right ) ) + 1;
        return curr;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> t ) {
        if( t == null )
            return -1;

        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param item the item to insert.
     * @param curr the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType item, AvlNode<AnyType> curr ) {
        if( curr == null )
            return new AvlNode<>( item, null, null );

        int compareResult = item.compareTo( curr.element );

        if( compareResult < 0 )
            curr.left = insert( item, curr.left );
        else
            curr.right = insert( item, curr.right );

        return balance( curr );
    }


//    private AvlNode<WordInfo> insert(WordInfo item, AvlNode<WordInfo> curr ) {
//        if( curr == null )
//            return new AvlNode<WordInfo>( item, null, null );
//
//        if( item.priority < curr.element.priority )
//            curr.left = insert( item, curr.left );
//        else
//            curr.right = insert( item, curr.right );
//
//        return balance( curr );
//    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param curr the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> curr ) {
        if( curr == null )
            return curr;

        while( curr.left != null )
            curr = curr.left;
        return curr;
    }



    private AvlNode<AnyType> traverseToLowestNode(AvlNode<AnyType> curr) {
        if (curr == root && root.left == null) {
            curr = curr.right;
        }
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param curr the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> curr ) {
        if( curr == null )
            return curr;

        while( curr.right != null )
            curr = curr.right;
        return curr;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param item is item to search for.
     * @param curr the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType item, AvlNode<AnyType> curr ) {
        while( curr != null )
        {
            int compareResult = item.compareTo( curr.element );

            if( compareResult < 0 )
                curr = curr.left;
            else if( compareResult > 0 )
                curr = curr.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param curr the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> curr, String tree ) {
        if( curr != null )
        {
            printTree( curr.right, tree+"   " );
            System.out.println( tree+ curr.element + "("+ curr.height  +")" );
            printTree( curr.left, tree+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> curr ) {
        if (curr==null)
            return -1;
        return curr.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> curr ) {
        AvlNode<AnyType> theLeft = curr.left;
        curr.left = theLeft.right;
        theLeft.right = curr;
        curr.height = Math.max( height( curr.left ), height( curr.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), curr.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> curr ) {
        AvlNode<AnyType> theRight = curr.right;
        curr.right = theRight.left;
        theRight.left = curr;
        curr.height = Math.max( height( curr.left ), height( curr.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), curr.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> curr ) {
        curr.left = leftRotation( curr.left );
        return rightRotation( curr );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> curr ) {
        curr.right = rightRotation( curr.right );
        return leftRotation( curr );
    }

    private static class AvlNode<AnyType> {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> leftNode, AvlNode<AnyType> rightNode )
        {
            element  = theElement;
            left     = leftNode;
            right    = rightNode;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    private AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}
