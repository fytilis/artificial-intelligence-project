public class Cell{
    boolean isBlocked;
    boolean isStart=false;
    boolean isGoal = false;
    boolean isTeleportA = false;
    boolean isTeleportB = false;
  public String toString() {
        if (isStart) return "S";
        if (isGoal) return "G";
        if (isTeleportA) return "A";
        if (isTeleportB) return "B";
        return isBlocked ? "#" : ".";
    }
}
