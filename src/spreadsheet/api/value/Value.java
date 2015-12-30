package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public interface Value {
    void visit(ValueVisitor visitor);
}
