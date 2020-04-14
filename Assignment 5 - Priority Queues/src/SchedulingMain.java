public class SchedulingMain {
    public static void main(String[] args) throws Exception{
        System.out.println("Taskset1:");
        ScheduleTasks.scheduleTasks("taskset1.txt");
        System.out.println("\nTaskset2:");
        ScheduleTasks.scheduleTasks("taskset2.txt");
        System.out.println("\nTaskset3:");
        ScheduleTasks.scheduleTasks("taskset3.txt");
        System.out.println("\nTaskset4:");
        ScheduleTasks.scheduleTasks("taskset4.txt");
    }
}
