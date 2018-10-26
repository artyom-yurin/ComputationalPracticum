public class ExactSolution extends SolutionMethod {

    public ExactSolution(int size, float x0, float y0, float xMax) {
        super(size, x0, y0, xMax);
        CalculateFunction();
    }

    @Override
    public void CalculateFunction() {
        if(super.needCalculate)
        {
            float step = (xMax - x0) / (float) (super.size - 1);
            super.AxisX[0] = x0;
            super.AxisY[0] = y0;
            for (int i = 1; i < super.size; i++) {
                super.AxisX[i] = super.AxisX[i - 1] + step;
                super.AxisY[i] = - super.AxisX[i] * super.AxisX[i] + 2 * super.AxisX[i] - 2 + (y0 + x0 * x0 - 2 * x0 + 2) * (float)Math.exp(x0 - super.AxisX[i]);
            }
            super.needCalculate = false;
        }
    }
}
