import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTasks {

    public static void scheduleTasks(String file) {
        PriorityQueue queue = new PriorityQueue();
        ArrayList<Task2> tasks = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<String>();
        try {
            List<String> allLines = Files.readAllLines(Paths.get(file));
            for (String line : allLines) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < lines.size(); i++) {
            List<Integer> numberList = new ArrayList<Integer>();
            for (String numberText : lines.get(i).trim().split("\\s+")) {
                numberList.add(Integer.valueOf(numberText));
            }
            Task2 task = new Task2(i + 1, numberList.get(0), numberList.get(1), numberList.get(2));
            tasks.add(task);
        }
        for (int i = 0; i < tasks.size(); i++) {
            queue.insert(tasks.get(i), tasks.get(i).priority());
        }

        int time = 1;
        int totalLate = 0;
        int lateTime = 0;
        for (int i = 0; i < queue.getSize(); i++) {
            Node<Task2> task = queue.removeMin();

            for (int j = 0; j < task.element.duration; j++) {
                String line = "Time: " + time;
                line += " " + task.element.toString();
                if (j + 1 == task.element.duration) {
                    line += " ** ";
                    if (time > task.element.deadline) {
                        line += "Late " + (time - task.element.deadline);
                        totalLate += 1;
                        lateTime += (time - task.element.deadline);
                    }
                }

                System.out.println(line);
                time++;
            }
        }
        System.out.println("Tasks late " + totalLate + "  total time late " + lateTime);
    }
}


