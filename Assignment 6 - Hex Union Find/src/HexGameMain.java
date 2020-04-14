import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HexGameMain {

    // The color for the letters
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // Main where the program calls which games to play
    public static void main(String[] args) {
        playGame("moves.txt");
        playGame("Moves2.txt");

    }

    // Where the playing of the game gets called
    // This method does all the work for the game
    public static void playGame(String nameOfFile) {
        DisjointSet gameGrid1 = new DisjointSet();
        boolean blueWon = false;
        boolean redWon = false;
        int movesCount = 0;
        ArrayList<Integer> game1List = readFileToArrayList(nameOfFile);
        for (int number = 0; number < game1List.size(); number++) {
            if (gameGrid1.itemList[game1List.get(number)].element != "B" ||
                    gameGrid1.itemList[game1List.get(number)].element != "R") {
                if (number % 2 == 0) {
                    gameGrid1.itemList[game1List.get(number)].element = ANSI_BLUE + "B" + ANSI_RESET;
                    if (number == 16) {
                        System.out.println();
                    }
                    setEdges(gameGrid1, game1List.get(number));
                    checkUnion(gameGrid1, game1List.get(number));

                } else {
                    gameGrid1.itemList[game1List.get(number)].element = ANSI_RED + "R" + ANSI_RESET;
                    setEdges(gameGrid1, game1List.get(number));
                    checkUnion(gameGrid1, game1List.get(number));
                }
            }
            for (int i = 0; i < number; i++) {
                if (gameGrid1.itemList[game1List.get(i)].edge != null &&
                        gameGrid1.itemList[game1List.get(i)].edge.equals("Connected")) {
                    if (gameGrid1.itemList[game1List.get(i)].element.equals(ANSI_BLUE + "B" + ANSI_RESET)) {
                        blueWon = true;
                    } else if (gameGrid1.itemList[game1List.get(i)].element.equals(ANSI_RED + "R" + ANSI_RESET)) {
                        redWon = true;
                    }
                    break;
                }
            }
            movesCount = number;
        }

        if (blueWon) {
            System.out.println("--------> Blue has won after " + movesCount + " attempted " +
                    "moves! Here is the final board.");
        } else if (redWon) {
            System.out.println("--------> Red has won after " + movesCount + " attempted " +
                    "moves! Here is the final board.");
        } else {
            System.out.println("--------> Nobody won after " + movesCount + " attempted " +
                    "moves. Here is the final board.");
        }
        printGame(gameGrid1);

    }


    // Prints out the hex game
    private static void printGame(DisjointSet game) {
        String spacer = " ";
        for (int i = 1; i < game.itemList.length + 1; i++) {
            System.out.print(game.itemList[i - 1].element + " ");
            if (i % 11 == 0) {
                System.out.print("\n" + spacer);
                spacer += " ";
            }
        }
    }

    // Reads the same file and puts into into an ArrayList to be used in playGame()
    private static ArrayList<Integer> readFileToArrayList(String fileName) {
        ArrayList<Integer> movesList = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                movesList.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return movesList;
    }

    // Marks which hexes are touching an edge, and what edge they're touching
    public static void setEdges(DisjointSet set, int index) {
            if (index - 11 < 0 && set.itemList[index].element == ANSI_RED + "R" + ANSI_RESET) {
                set.itemList[index].edge = "Top";
            } else if (index + 10 > set.itemList.length && set.itemList[index].element == ANSI_RED + "R" + ANSI_RESET) {
                set.itemList[index].edge = "Bottom";
            } else if (index % 11 == 0 && set.itemList[index].element == ANSI_BLUE + "B" + ANSI_RESET) {
                set.itemList[index].edge = "Right";
            } else if (index % 11 == 1 && set.itemList[index].element == ANSI_BLUE + "B" + ANSI_RESET) {
                set.itemList[index].edge = "Left";
            }
    }

    // This checks and sees if any hex needs to be unioned with another one
    // If it gets unioned with one it goes to which one its unioned with and checks edges so link everything
    public static void checkUnion(DisjointSet set, int index) {
        if (index == 114) {
            System.out.println();
        }
        if (set.itemList[index].element == ANSI_RED + "R" + ANSI_RESET) {
            if (index - 11 > 0) {
                if (set.itemList[index - 11].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index - 11);
                    if (set.itemList[index].edge != null && set.itemList[index - 11].edge != null) {
                        if ((set.itemList[index].edge.equals("Top") && set.itemList[index - 11].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index - 11].edge.equals("Top")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 11].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 11].edge = set.itemList[index].edge;
                            checkUnion(set, index - 11);
                        } else if (set.itemList[index - 11].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 11].edge;
                            checkUnion(set, index - 11);
                        }
                    }
                }
            }
            if (index - 10 > 0) {
                if (set.itemList[index - 10].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index - 10);
                    if (set.itemList[index].edge != null && set.itemList[index - 10].edge != null) {
                        if ((set.itemList[index].edge.equals("Top") && set.itemList[index - 10].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index - 10].edge.equals("Top")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 10].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 10].edge = set.itemList[index].edge;
                            checkUnion(set, index - 10);
                        } else if (set.itemList[index - 10].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 10].edge;
                            checkUnion(set, index - 10);
                        }
                    }
                }
            }
            if (index - 1 > 0) {
                if (set.itemList[index - 1].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index - 1);
                    if (set.itemList[index].edge != null && set.itemList[index - 1].edge != null) {
                        if ((set.itemList[index].edge.equals("Top") && set.itemList[index - 1].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index - 1].edge.equals("Top")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 1].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 1].edge = set.itemList[index].edge;
                            checkUnion(set, index - 1);
                        } else if (set.itemList[index - 1].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 1].edge;
                            checkUnion(set, index - 1);
                        }
                    }
                }
            }
            if (index + 1 < set.itemList.length) {
                if (set.itemList[index + 1].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index + 1);
                    if (set.itemList[index].edge != null && set.itemList[index + 1].edge != null) {
                        if ((set.itemList[index].edge.equals("Top") && set.itemList[index + 1].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index + 1].edge.equals("Top")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 1].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 1].edge = set.itemList[index].edge;
                            checkUnion(set, index + 1);
                        } else if (set.itemList[index + 1].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 1].edge;
                            checkUnion(set, index + 1);
                        }
                    }
                }
            }
            if (index + 10 < set.itemList.length) {
                if (set.itemList[index + 10].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index + 10);
                    if (set.itemList[index].edge != null && set.itemList[index + 10].edge != null) {
                        if ((set.itemList[index].edge.equals("Top") && set.itemList[index + 10].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index + 10].edge.equals("Top")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 10].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 10].edge = set.itemList[index].edge;
                            checkUnion(set, index + 10);
                        } else if (set.itemList[index + 10].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 10].edge;
                            checkUnion(set, index + 10);
                        }
                    }
                }
            }
            if (index + 11 < set.itemList.length) {
                if (set.itemList[index + 11].element == ANSI_RED + "R" + ANSI_RESET) {
                    set.union(index, index + 11);
                    if (set.itemList[index].edge != null && set.itemList[index + 11].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index + 11].edge.equals("Bottom")) ||
                                set.itemList[index].edge.equals("Bottom") && set.itemList[index + 11].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 11].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 11].edge = set.itemList[index].edge;
                            checkUnion(set, index + 11);
                        } else if (set.itemList[index + 11].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 11].edge;
                            checkUnion(set, index + 11);
                        }
                    }
                }
            }
        } else if (set.itemList[index].element == ANSI_BLUE + "B" + ANSI_RESET) {
            if (index - 11 > 0) {
                if (set.itemList[index - 11].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index - 11);
                    if (set.itemList[index].edge != null && set.itemList[index - 11].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index - 11].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index - 11].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 11].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 11].edge = set.itemList[index].edge;
                            checkUnion(set, index - 11);
                        } else if (set.itemList[index - 11].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 11].edge;
                            checkUnion(set, index - 11);
                        }
                    }
                }
            }
            if (index - 10 > 0) {
                if (set.itemList[index - 10].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index - 10);
                    if (set.itemList[index].edge != null && set.itemList[index - 10].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index - 10].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index - 10].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 10].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 10].edge = set.itemList[index].edge;
                            checkUnion(set, index - 10);
                        } else if (set.itemList[index - 10].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 10].edge;
                            checkUnion(set, index - 10);
                        }
                    }
                }
            }
            if (index - 1 > 0) {
                if (set.itemList[index - 1].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index - 1);
                    if (set.itemList[index].edge != null && set.itemList[index - 1].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index - 1].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index - 1].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index - 1].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index - 1].edge = set.itemList[index].edge;
                            checkUnion(set, index - 1);
                        } else if (set.itemList[index - 1].edge != null) {
                            set.itemList[index].edge = set.itemList[index - 1].edge;
                            checkUnion(set, index - 1);
                        }
                    }
                }
            }
            if (index + 1 < set.itemList.length) {
                if (set.itemList[index + 1].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index + 1);
                    if (set.itemList[index].edge != null && set.itemList[index + 1].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index + 1].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index + 1].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 1].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 1].edge = set.itemList[index].edge;
                            checkUnion(set, index + 1);
                        } else if (set.itemList[index + 1].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 1].edge;
                            checkUnion(set, index + 1);
                        }
                    }
                }
            }
            if (index + 10 < set.itemList.length) {
                if (set.itemList[index + 10].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index + 10);
                    if (set.itemList[index].edge != null && set.itemList[index + 10].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index + 10].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index + 10].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 10].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 10].edge = set.itemList[index].edge;
                            checkUnion(set, index + 10);
                        } else if (set.itemList[index + 10].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 10].edge;
                            checkUnion(set, index + 10);
                        }
                    }
                }
            }
            if (index + 11 < set.itemList.length) {
                if (set.itemList[index + 11].element == ANSI_BLUE + "B" + ANSI_RESET) {
                    set.union(index, index + 11);
                    if (set.itemList[index].edge != null && set.itemList[index + 11].edge != null) {
                        if ((set.itemList[index].edge.equals("Right") && set.itemList[index + 11].edge.equals("Left")) ||
                                set.itemList[index].edge.equals("Left") && set.itemList[index + 11].edge.equals("Right")) {
                            set.itemList[index].edge = "Connected";
                            set.itemList[index + 11].edge = "Connected";
                        }
                    } else {
                        if (set.itemList[index].edge != null) {
                            set.itemList[index + 11].edge = set.itemList[index].edge;
                            checkUnion(set, index + 11);
                        } else if (set.itemList[index + 11].edge != null) {
                            set.itemList[index].edge = set.itemList[index + 11].edge;
                            checkUnion(set, index + 11);
                        }
                    }
                }
            }
        }
    }
}
