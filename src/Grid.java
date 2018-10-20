public class Grid {
    protected float AxisX[];
    protected float AxisY[];
    protected int size;

    Grid(int size)
    {
        AxisX = new float[size + 1];
        AxisY = new float[size + 1];
        this.size = size;
    }

    public float[] getAxisX() {
        return AxisX;
    }

    public void setAxisX(float[] axisX) {
        AxisX = axisX;
    }

    public float[] getAxisY() {
        return AxisY;
    }

    public void setAxisY(float[] axisY) {
        AxisY = axisY;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        AxisX = new float[size + 1];
        AxisY = new float[size + 1];
    }
}
