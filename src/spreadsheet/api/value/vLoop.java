package spreadsheet.api.value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class vLoop implements Value {
    public static final vLoop INSTANCE = new vLoop();

    private vLoop(){

    }

    @Override
    public void visit(ValueVisitor visitor) {
        visitor.visitLoop();
    }

    @Override
    public String toString() {
        return "#LOOPREF";
    }
}
