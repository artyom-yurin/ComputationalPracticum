public class KuttaMethod extends EulerMethod {
    KuttaMethod(int size, float x0, float y0, int xMax) {
        super(size, x0, y0, xMax);
    }

    @Override
    protected float CalculateY(int i, float step) {
        float k1 = getRhs().calculate(super.AxisX[i - 1], super.AxisY[i - 1]);;
        float k2 = getRhs().calculate(super.AxisX[i - 1] + step / 2, super.AxisY[i - 1] + step / 2 * k1);
        float k3 = getRhs().calculate(super.AxisX[i - 1] + step / 2, super.AxisY[i - 1] + step / 2 * k2);
        float k4 = getRhs().calculate(super.AxisX[i], super.AxisY[i - 1] + step * k3);
        return super.AxisY[i-1] + step / 6 * (k1 + 2 * k2 + 2 * k3 + k4);
    }
}
