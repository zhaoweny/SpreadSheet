package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class vDouble implements Value {
    private final double value;

    public vDouble(double value) {
        this.value = value;
    }

    @Override
    public void visit(ValueVisitor visitor) {
        visitor.visitNumber(value);
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        vDouble that = (vDouble) o;

        return Double.compare(that.value, value) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
