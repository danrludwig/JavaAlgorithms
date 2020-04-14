// Run the tests here
public class TestLadder {
    public static void main(String[] args) {

        int RANDOMCT = 7;
        LadderGame g = new LadderGame("dictionary.txt");
//        g.listWords(10, 6);  //Lists the first ten words in the dictionary of length 6 as a test.
        g.play("kiss", "woof");
        g.play("cock", "numb");
        g.play("jura", "such");
        g.play("stet", "whey");
        g.play("rums", "numb");
        g.play("coke", "warm"); // This is an example of the AVL Tree getting the solution in 1/2 the queues
    }

}