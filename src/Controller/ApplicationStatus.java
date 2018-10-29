package Controller;


import Model.*;

public class ApplicationStatus {
    public enum State {SOLUTION, ERROR, ANALYSIS_ERROR}

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

    public static Grid eulerGlobalError;
    public static Grid kuttaGlobalError;
    public static Grid improvedEulerGlobalError;

    public static int size;
    public static float x0;
    public static float y0;
    public static float xMax;
    public static int maxSize;

    public static void init(int size, float x0, float y0, float xMax, int maxSize) throws CloneNotSupportedException {
        SetNewParameters(size, x0, y0, xMax);
        ApplicationStatus.maxSize = maxSize;

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

        eulerGlobalError = GetGlobalError(eulerMethod);
        improvedEulerGlobalError = GetGlobalError(improvedEulerMethod);
        kuttaGlobalError = GetGlobalError(kuttaMethod);
    }

    public static boolean Recalculate(int size, float x0, float y0, float xMax, int maxSize) throws CloneNotSupportedException {
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

            if( ApplicationStatus.maxSize != maxSize) {
                ApplicationStatus.maxSize = maxSize;

                eulerGlobalError = GetGlobalError(eulerMethod);
                improvedEulerGlobalError = GetGlobalError(improvedEulerMethod);
                kuttaGlobalError = GetGlobalError(kuttaMethod);
            }

                return true;
        }
        else if( ApplicationStatus.maxSize != maxSize)
        {
            ApplicationStatus.maxSize = maxSize;

            eulerGlobalError = GetGlobalError(eulerMethod);
            improvedEulerGlobalError = GetGlobalError(improvedEulerMethod);
            kuttaGlobalError = GetGlobalError(kuttaMethod);

            if(currentState == State.ANALYSIS_ERROR)
            {
                return true;
            }
        }
        return false;
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
            axisY[i] = Math.abs(exactAxisY[i] - someAxisY[i]);
        }
        return error;
    }

    private static Grid GetGlobalError(SolutionMethod someMethod) throws CloneNotSupportedException {


        Grid globalError = new Grid(ApplicationStatus.maxSize - ApplicationStatus.size + 1);
        SolutionMethod solutionMethod = (SolutionMethod)someMethod.clone();
        SolutionMethod exactMethod = (SolutionMethod)exactSolution.clone();

        float[] axisX = globalError.getAxisX();
        float[] axisY = globalError.getAxisY();

        for(int i = 0; i < ApplicationStatus.maxSize - ApplicationStatus.size + 1; i++)
        {
            int x = ApplicationStatus.size + i;
            solutionMethod.setSize(x);
            exactMethod.setSize(x);
            exactMethod.CalculateFunction();
            solutionMethod.CalculateFunction();
            float[] solutionAxisY = solutionMethod.getAxisY();
            float[] exactAxisY = exactMethod.getAxisY();
            float y = 0;
            for (int j = 0; j < x; j++) {
                float currentY = Math.abs(exactAxisY[j] - solutionAxisY[j]);
                if (y < currentY)
                {
                    y = currentY;
                }
            }
            axisX[i] = x;
            axisY[i] = y;
        }

        return globalError;
    }
}
