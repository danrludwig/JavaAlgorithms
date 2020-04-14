//public class Term<AnyType implements Comparable<? super AnyType>>{

//implements Comparable<Task2>
public class Task2  {
    public int ID;
    public int start;
    public int deadline;
    public int duration;
    public int priority;

    public Task2(int ID, int start, int deadline, int duration) {
        this.ID = ID;
        this.start = start;
        this.deadline = deadline;
        this.duration = duration;
        createPriority();
    }

    public String toString() {
        return "Task " + ID;
    }

    public String toStringL() {
        return "Task " + ID + "[" + start + "-" + deadline + "] " + duration;
    }

    // 3 createPriority() methods are written, but only one isn't commented out.
    // To test other methods comment out the current one and uncomment the one you want to use.

//    private void createPriority() {
//        this.priority = start;
//    }

    private void createPriority() {
        this.priority = deadline;
    }
//
//    private void createPriority() {
//        this.priority = duration;
//    }

    public int priority() {
        return priority;
    }

}