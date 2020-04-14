public class DisjointSet<E extends Comparable> {
//    public int[] parent;
    public Hex[] itemList;
    private int size;

    // Default constructor
    public DisjointSet() {
        this.size = 121;
//        this.parent = new int[this.size];
        this.itemList = new Hex[this.size];
        createSet();
//        setEdges();
    }

    // Constructor
    public DisjointSet(int size) {
        this.size = size;
//        this.parent = new int[this.size];
        this.itemList = new Hex[this.size];
        createSet();
//        setEdges();
    }

    // Creates n sets with index -1 in each
    // this is going to keep track of how large the group is
    // if its the root node, else it will change to the
    // parent index number.
    private void createSet() {
        for (int i = 0; i < this.size; i++) {
            itemList[i] = new Hex(-1, 0,i);
        }
    }

    // Sets the edges and says which edge it has


    // Returns representative of x's set
    public int find(int index) {
        if (itemList[index].parent >= 0) {
            int oldIndex = index;
            index = find(itemList[index].parent);
            itemList[oldIndex].parent = index;
        }
        return index;
    }

    // Unites the set that includes x and the set that includes y
    public void union(int index1, int index2) {
        index1 = find(index1);
        index2 = find(index2);

        if (itemList[index1] == itemList[index2]) {
            return;
        } else if (itemList[index1].parent < itemList[index2].parent) {
            itemList[index1].parent += itemList[index2].parent;
            itemList[index2].parent = itemList[index1].index;
        } else {
            itemList[index2].parent += itemList[index1].parent;
            itemList[index1].parent = itemList[index2].index;
        }
    }

    // Test the code
    public static class Main {
        public static void main(String[] args) {
            DisjointSet set = new DisjointSet(10);
            set.union(0,1);
            set.union(2,3);
            set.union(4,5);
            set.union(6,7);
            set.union(8,9);
            set.union(3,1);
            set.union(0, 7);
            set.union(5, 9);
            set.union(5, 6);
            set.union(5, 6);

        }
    }
}

