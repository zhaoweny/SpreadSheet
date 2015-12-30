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
    public String toString() {
        return String.format("{%s}", exp);
    }
}
