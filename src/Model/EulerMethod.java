package Model;

public class EulerMethod extends SolutionMethod {
    private RHS rhs;


    public EulerMethod(int size, float x0, float y0, float xMax) {
        super(size,x0,y0,xMax);
        this.rhs = new RHS();
        CalculateFunction();
    }

    protected float CalculateY(int i, float step)
    {
        return super.AxisY[i - 1] + step * rhs.calculate(AxisX[i - 1], AxisY[i - 1]);
    }

    public RHS getRhs() {
        return rhs;
    }

    @Override
    public void CalculateFunction() {
        if(super.needCalculate)
        {
            float step = (xMax - x0) / (float) (super.size - 1);
            super.AxisX[0] = x0;
            super.AxisY[0] = y0;
            for (int i = 1; i < size; i++) {
                super.AxisX[i] = super.AxisX[i - 1] + step;
                super.AxisY[i] = CalculateY(i, step);
            }
            super.needCalculate = false;
        }
    }
}
