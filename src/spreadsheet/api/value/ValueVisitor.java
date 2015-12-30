package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public interface ValueVisitor {
    void visitLoop();

    void visitInvalid(String exp);

    void visitNumber(double val);

    void visitString(String exp);
}
