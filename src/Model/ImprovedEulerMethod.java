package Model;

public class ImprovedEulerMethod extends EulerMethod{

    public ImprovedEulerMethod(int size, float x0, float y0, float xMax) {
        super(size, x0, y0, xMax);
    }

    @Override
    protected float CalculateY(int i, float step) {
        float k1 = getRhs().calculate(super.AxisX[i - 1], super.AxisY[i - 1]);
        float k2 = getRhs().calculate(super.AxisX[i], super.AxisY[i-1] + step * k1);
        return super.AxisY[i - 1] + step / 2 * (k1 + k2);
    }
}
