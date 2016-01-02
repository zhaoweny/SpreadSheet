package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class vInvalid implements Value {
    private final String exp;

    public vInvalid(String exp) {
        this.exp = exp;
    }

    @Override
    public void visit(ValueVisitor visitor) {
        visitor.visitInvalid(exp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        vInvalid vInvalid = (vInvalid) o;

        return exp != null ? exp.equals(vInvalid.exp) : vInvalid.exp == null;

    }

    @Override
    public int hashCode() {
        return exp != null ? exp.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("{%s}", exp);
    }
}
