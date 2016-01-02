package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class vString implements Value {
    private final String exp;
    public vString(String exp){
        this.exp = exp;
    }

    @Override
    public void visit(ValueVisitor visitor) {
        visitor.visitString(exp);
    }

    @Override
    public String toString() {
        return exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        vString vString = (vString) o;

        return exp != null ? exp.equals(vString.exp) : vString.exp == null;

    }

    @Override
    public int hashCode() {
        return exp != null ? exp.hashCode() : 0;
    }
}
