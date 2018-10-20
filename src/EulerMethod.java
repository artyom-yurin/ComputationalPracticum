public class EulerMethod extends Grid {

    private float x0;
    private float y0;
    private float xMax;
    private float step;
    private RHS rhs;


    EulerMethod(int size, float x0, float y0, int xMax) {
        super(size);
        this.x0 = x0;
        this.y0 = y0;
        this.xMax = xMax;
        calculateStep();
        calculateFunction();
    }

    private void calculateStep()
    {
        step = (xMax - x0) / (float)super.size;
    }

    protected void calculateFunction()
    {
        super.AxisX[0] = x0;
        super.AxisY[0] = y0;
        for(int i = 1; i < size + 1; i++)
        {
            super.AxisX[i] = super.AxisX[i-1] + step;
            super.AxisY[i] = super.AxisY[i-1] + step * rhs.calculate(AxisX[i-1], AxisY[i-1]);
        }
    }

    @Override
    public void setSize(int size)
    {
        super.setSize(size);
        calculateStep();
        calculateFunction();
    }

    public float getStep() {
        return step;
    }

    public float getX0() {
        return x0;
    }

    public void setX0(float x0) {
        this.x0 = x0;
        calculateStep();
    }

    public float getxMax() {
        return xMax;
    }

    public void setxMax(float xMax) {
        this.xMax = xMax;
        calculateStep();
    }

    public float getY0() {
        return y0;
    }

    public void setY0(float y0) {
        this.y0 = y0;
    }

    public RHS getRhs() {
        return rhs;
    }

    public void setRhs(RHS rhs) {
        this.rhs = rhs;
    }
}
