// A Hex class to store all the information for each hex in the game

public class Hex<E extends Comparable> {
    public int parent;
    public E element;
    public int index;
    public String edge;

    public Hex(int parent, E element, int index) {
        this.parent = parent;
        this.element = element;
        this.index = index;
        this.edge = null;
    }
}
