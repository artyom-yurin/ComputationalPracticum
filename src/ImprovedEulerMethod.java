public class ImprovedEulerMethod extends EulerMethod{
    
    ImprovedEulerMethod(int size, float x0, float y0, int xMax) {
        super(size, x0, y0, xMax);
    }

    @Override
    protected float CalculateY(int i) {
        return super.AxisY[i - 1] +
                super.getStep() / 2 *
                        (getRhs().calculate(super.AxisX[i - 1], super.AxisY[i - 1]) +
                                getRhs().calculate(super.AxisX[i], super.AxisY[i-1] + super.getStep() * super.getRhs().calculate(super.AxisX[i-1], super.AxisY[i-1])));
    }
}
