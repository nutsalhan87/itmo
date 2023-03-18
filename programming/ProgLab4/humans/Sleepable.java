package humans;

public interface Sleepable {
    public boolean getSleepCondition();
    public void wakeUp();
    public void sleep();
    public void toWakeSomeone(Human human);
}
