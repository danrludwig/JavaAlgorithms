// Author: Dan Ludwig
// CS 2420
// Assignment 1

import java.util.*;
import java.io.File;

public class LadderGame {
    static int MaxWordSize = 15;
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;

    public LadderGame(String file) {
        random = new Random();
        allList = new ArrayList[MaxWordSize];
        for (int i = 0; i < MaxWordSize; i++)
        {
            allList[i] = new ArrayList<String>();
        }
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNext()) {
                String word = reader.next();
                if (word.length() < MaxWordSize)
                {
                    allList[word.length()].add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String a, String b) {

        if (a.length() != b.length()){
            System.out.println("Words are not the same length");
            return;
        }
        if (a.length()  >= MaxWordSize) return;
        ArrayList list = new ArrayList();
        ArrayList<String> l = allList[a.length()];
        list = (ArrayList) l.clone();
        System.out.println("Queue: Seeking a solution from " + a + " ->" + b + " Size of List " + list.size());
        findLadder(a, b);
        System.out.println("AVLTree: Seeking a solution from " + a + " ->" + b + " Size of List " + list.size());
        findAvlTreeLadder(a, b);
    }

    public void play(int len) {
        if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String a = list.get(random.nextInt(list.size()));
        String b = list.get(random.nextInt(list.size()));
        play(a, b);
    }

    public void listWords(int numWords, int wordSize) {
        for (int i=0; i < numWords; i++) {
            System.out.println(allList[wordSize].get(i));
        }
    }


    //This method actaully creates the queue
    private void createLadder(ArrayList<String> wordsOfLengthN, String startWord, String goalWord) {
        boolean done = false;
        // Creates the queue and put the first item in the queue
        QueueLink wordLadder = new QueueLink();
        int letterDifference = numberOfCharDifference(startWord, goalWord);
        WordInfo firstWordInfo = new WordInfo(startWord, 0, startWord, letterDifference);
        wordLadder.enqueue(firstWordInfo);
        int enqueueCount = 0;

        // The while loop runs through every list in the queue
        while (!wordLadder.isEmpty() && !done) {
            WordInfo oldWordInfo = (WordInfo)(wordLadder.peek());

            // Checks each word against the word in the queue
            for (int i = 0; i < wordsOfLengthN.size(); i++) {
                String checkWord = oldWordInfo.word;
                String otherWord = wordsOfLengthN.get(i);
                String history = oldWordInfo.history;

                // if the words are only one letter apart then it creates a WordInfo object and adds it to the back
                // of the queue
                if (isOneCharacterAway(checkWord, otherWord)) {
                    history += " " + otherWord;
                    String[] listOfWords = history.split(" ");
                    int ladderSize = listOfWords.length - 1;
                    letterDifference = numberOfCharDifference(checkWord, otherWord);
                    WordInfo newInfo = new WordInfo(otherWord, ladderSize, history, letterDifference);
                    wordLadder.enqueue(newInfo);
                    wordsOfLengthN.remove(otherWord);
                    enqueueCount++;
//                    System.out.println(newInfo.toString());  // Uncomment this to see the ladders build themselves.

                    // checks to see if the final word was found, when it is it prints the results of the word ladder
                    if (otherWord.equals(goalWord)) {
                        done = true;
                        System.out.println(newInfo.toString());
                        System.out.println("Number of enqueues: " + enqueueCount);
                        break;
                    }
                }
            }
            // After it is done using the queue it removes it and moves to the next one
            wordLadder.dequeue();
            if (wordLadder.isEmpty()) {
                System.out.println("No ladder found.");
            }
        }
    }

    // Method called to find the word ladder
    // This method adds the words at length X to another list that is used to build the ladder
    private void findLadder(String start, String end) {
        ArrayList<String> words = allList[start.length()];
        ArrayList<String> wordsOfLengthN = (ArrayList) words.clone();
        wordsOfLengthN.remove(start);
        if (!wordsOfLengthN.contains(end)) {
            System.out.println("The end word is not in the dictionary.");
            return;
        }
        createLadder(wordsOfLengthN, start, end);
    }

    // Method called to find the word ladder using an AVL Tree
    private void findAvlTreeLadder(String start, String end) {
        ArrayList<String> words = allList[start.length()];
        ArrayList<String> wordList = (ArrayList) words.clone();
        wordList.remove(start);
        if(!wordList.contains(end)) {
            System.out.println("The end word is not in the dictionary.");
            return;
        }
        createAvlTreeLadder(wordList, start, end);
    }

    // The method actually does the work with the AVL Tree and finds the word ladder
    // The method runs the same as the queue method, but inserts it into an AVL Tree instead
    private void createAvlTreeLadder(ArrayList<String> wordsOfLengthN, String startWord, String goalWord) {
        int enqueueCount = 0;
        boolean done = false;

        AVLTree wordLadder = new AVLTree<>();
        int letterDifference = numberOfCharDifference(startWord, goalWord);
        WordInfo firstWordInfo = new WordInfo(startWord, 0, startWord, letterDifference);
        wordLadder.insert(firstWordInfo);

        while (!wordLadder.isEmpty() && !done) {
            WordInfo oldWordInfo = (WordInfo)(wordLadder.findMin());

            for (int i=0; i < wordsOfLengthN.size(); i++) {
                String checkWord = oldWordInfo.word;
                String otherWord = wordsOfLengthN.get(i);
                String history = oldWordInfo.history;

                if (isOneCharacterAway(checkWord, otherWord)) {
                    history += " " + otherWord;
                    String[] listOfWords = history.split(" ");
                    int ladderSize = listOfWords.length - 1;
                    letterDifference = numberOfCharDifference(checkWord, otherWord);
                    WordInfo newInfo = new WordInfo(otherWord, ladderSize, history, letterDifference);
                    wordLadder.insert(newInfo);
                    wordsOfLengthN.remove(otherWord);
                    enqueueCount++;

                    if (otherWord.equals(goalWord)) {
                        done = true;
                        System.out.println(newInfo.toString());
                        System.out.println("Number of enqueues: " + enqueueCount);
                        System.out.println();
                        break;
                    }
                }
            }

            wordLadder.deleteMin();
            if (wordLadder.isEmpty()) {
                System.out.println("No ladder found.");
            }
        }
    }

    // Method to check if a word is only one character away or not
    public boolean isOneCharacterAway(String firstWord, String secondWord) {
        int count = 0;
        for (int i = 0; i < firstWord.length() && i < secondWord.length(); i++) {
            if (firstWord.charAt(i) != secondWord.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }

    // Method to get number of characters that are different from each word
    private int numberOfCharDifference(String firstWord, String secondWord) {
        int count = 0;
        for (int i=0; i < firstWord.length() && i < secondWord.length(); i++) {
            if (firstWord.charAt(i) != secondWord.charAt(i)) {
                count++;
            }
        }
        return count;
    }

}