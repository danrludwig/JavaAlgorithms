public class WordInfo implements Comparable<WordInfo> {
    public String word;
    public int moves;
    public String history;
    public int priority;

    public WordInfo(String word, int moves, String history, int letterDifference){
        this.word = word;
        this.moves = moves;
        this.history = history;
        this.priority = moves + letterDifference;
    }

    @Override
    public int compareTo(WordInfo b) {
        if(this.priority > b.priority) {
            return 1;
        } else {
            return -1;
        }
    }


    public String toString(){
        return "Word " + word    + " Moves " +moves  + " History ["+history +"]";
    }

}