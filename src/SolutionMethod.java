public abstract class SolutionMethod extends Grid {

    protected float x0;
    protected float y0;
    protected float xMax;
    protected boolean needCalculate;

    SolutionMethod(int size, float x0, float y0, float xMax) {
        super(size);
        this.x0 = x0;
        this.y0 = y0;
        this.xMax = xMax;
        needCalculate = true;
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
        needCalculate = true;
    }

    public float getX0() {
        return x0;
    }

    public void setX0(float x0) {
        this.x0 = x0;
        needCalculate = true;
    }

    public float getY0() {
        return y0;
    }

    public void setY0(float y0) {
        this.y0 = y0;
        needCalculate = true;
    }

    public float getxMax() {
        return xMax;
    }

    public void setxMax(float xMax) {
        this.xMax = xMax;
        needCalculate = true;
    }

    public boolean isNeedCalculate() {
        return needCalculate;
    }

    public abstract void CalculateFunction();
}
