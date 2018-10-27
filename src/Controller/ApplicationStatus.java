package Controller;


import Model.*;

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

    public static int size;
    public static float x0;
    public static float y0;
    public static float xMax;

    public static void init(int size, float x0, float y0, float xMax)
    {
        SetNewParameters(size, x0, y0, xMax);

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

    public static boolean Recalculate(int size, float x0, float y0, float xMax)
    {
        SetNewParameters(size, x0, y0, xMax);
        SetNewParametersForMethod(exactSolution);
        if(exactSolution.isNeedCalculate())
        {
            exactSolution.CalculateFunction();

            SetNewParametersForMethod(eulerMethod);
            eulerMethod.CalculateFunction();
            eulerError = GetError(eulerMethod);

            SetNewParametersForMethod(improvedEulerMethod);
            improvedEulerMethod.CalculateFunction();
            improvedEulerError = GetError(improvedEulerMethod);

            SetNewParametersForMethod(kuttaMethod);
            kuttaMethod.CalculateFunction();
            kuttaError = GetError(kuttaMethod);
        }
        else
        {
            return false;
        }
        return true;
    }

    private static void SetNewParameters(int size, float x0, float y0, float xMax)
    {
        ApplicationStatus.x0 = x0;
        ApplicationStatus.y0 = y0;
        ApplicationStatus.xMax = xMax;
        ApplicationStatus.size = size;
    }

    private static void SetNewParametersForMethod(SolutionMethod method)
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
        Grid error = new Grid(someMethod.getSize());
        float[] exactAxisX = exactSolution.getAxisX();
        float[] exactAxisY = exactSolution.getAxisY();

        float[] someAxisY = someMethod.getAxisY();

        float[] axisX = error.getAxisX();
        float[] axisY = error.getAxisY();
        for (int i = 0; i < error.getSize(); i++) {
            axisX[i] = exactAxisX[i];
            axisY[i] = exactAxisY[i] - someAxisY[i];
        }
        return error;
    }
}
