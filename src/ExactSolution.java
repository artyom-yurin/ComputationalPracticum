public class ExactSolution extends Grid {

    private float x0;
    private float y0;
    private float xMax;
    private float step;

    public ExactSolution(int size, float x0, float y0, float xMax) {
        super(size);
        this.x0 = x0;
        this.y0 = y0;
        this.xMax = xMax;
        calculateStep();
        calculateFunction();
    }

    private void calculateStep() {
        step = (xMax - x0) / (float) (super.size - 1);
    }

    private void calculateFunction() {
        super.AxisX[0] = x0;
        super.AxisY[0] = y0;
        for (int i = 1; i < super.size; i++) {
            super.AxisX[i] = super.AxisX[i - 1] + step;
            super.AxisY[i] = - super.AxisX[i] * super.AxisX[i] + 2 * super.AxisX[i] - 2 + (y0 + x0 * x0 - 2 * x0 + 2) * (float)Math.exp(x0 - super.AxisX[i]);
        }
    }
    @Override
    public void setSize(int size) {
        super.setSize(size);
        calculateStep();
        calculateFunction();
    }

    public float getX0() {
        return x0;
    }

    public float getY0() {
        return y0;
    }

    public float getxMax() {
        return xMax;
    }

    public float getStep() {
        return step;
    }
}
