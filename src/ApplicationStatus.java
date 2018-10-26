public class ApplicationStatus {
    public enum State {SOLUTION, ERROR}

    public static State currentState;

    public static boolean isExactPresent;

    public static boolean isEulerPresent;

    public static boolean isKuttaPresent;

    public static boolean isImprovedPresent;

    public static EulerMethod eulerMethod;
    public static ExactSolution exactSolution;
    public static KuttaMethod kuttaMethod;
    public static ImprovedEulerMethod improvedEulerMethod;

    public static Grid eulerError;
    public static Grid kuttaError;
    public static Grid improvedEulerError;

    public static void init(int size, float x0, float y0, float xMax)
    {
        currentState = State.SOLUTION;

        isExactPresent = false;
        isEulerPresent = false;
        isKuttaPresent = false;
        isImprovedPresent = false;

        exactSolution = new ExactSolution(size, x0, y0, xMax);
        eulerMethod = new EulerMethod(size, x0, y0, xMax);
        kuttaMethod = new KuttaMethod(size, x0, y0, xMax);
        improvedEulerMethod = new ImprovedEulerMethod(size, x0, y0, xMax);

        eulerError = GetError(eulerMethod);
        kuttaError = GetError(kuttaMethod);
        improvedEulerError = GetError(improvedEulerMethod);
    }

    public static void Recalculate(int size, float x0, float y0, float xMax)
    {
        SetupParameters(exactSolution, size, x0, y0, xMax);
        if(exactSolution.needCalculate)
        {
            exactSolution.CalculateFunction();

            SetupParameters(eulerMethod, size, x0, y0, xMax);
            eulerMethod.CalculateFunction();
            eulerError = GetError(eulerMethod);

            SetupParameters(improvedEulerMethod, size, x0, y0, xMax);
            improvedEulerMethod.CalculateFunction();
            improvedEulerError = GetError(improvedEulerMethod);

            SetupParameters(kuttaMethod, size, x0, y0, xMax);
            kuttaMethod.CalculateFunction();
            kuttaError = GetError(kuttaMethod);
        }
    }

    private static void SetupParameters(SolutionMethod method, int size, float x0, float y0, float xMax)
    {
        method.setSize(size);
        method.setX0(x0);
        method.setY0(y0);
        method.setxMax(xMax);
    }

    public static void clearGraphics()
    {
        isExactPresent = false;
        isEulerPresent = false;
        isKuttaPresent = false;
        isImprovedPresent = false;
    }

    private static Grid GetError(Grid someMethod) {
        Grid error = new Grid(someMethod.size);
        for (int i = 0; i < error.size; i++) {
            error.AxisX[i] = exactSolution.AxisX[i];
            error.AxisY[i] = exactSolution.AxisY[i] - someMethod.AxisY[i];
        }
        return error;
    }
}
