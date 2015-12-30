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
}
