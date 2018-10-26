public class Grid {
    protected float AxisX[];
    protected float AxisY[];
    protected int size;

    Grid(int size)
    {
        AxisX = new float[size];
        AxisY = new float[size];
        this.size = size;
    }

    public float[] getAxisX() {
        return AxisX;
    }

    public float[] getAxisY() {
        return AxisY;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        AxisX = new float[size];
        AxisY = new float[size];
    }
}
