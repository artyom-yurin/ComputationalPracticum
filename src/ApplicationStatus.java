public class ApplicationStatus {
    public enum State {SOLUTION, ERROR}

    public static State currentState;

    public static boolean isExactPresent;

    public static boolean isEulerPresent;

    public static boolean isKuttaPresent;

    public static boolean isImprovedPresent;

    public static void init()
    {
        currentState = State.SOLUTION;
        isExactPresent = false;
        isEulerPresent = false;
        isKuttaPresent = false;
        isImprovedPresent = false;
    }

    public static void clearGraphics()
    {
        isExactPresent = false;
        isEulerPresent = false;
        isKuttaPresent = false;
        isImprovedPresent = false;
    }
}
