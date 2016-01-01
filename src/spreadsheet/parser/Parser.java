package spreadsheet.parser;

import org.nfunk.jep.JEP;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class Parser {
    private Parser() {
    }

    private static JEP parse(String exp) {
        JEP jep = new JEP();
        jep.addStandardConstants();
        jep.addStandardFunctions();
        jep.parseExpression(exp);
        return jep;
    }

    public static double parseDouble(String exp) {
        JEP jep = parse(exp);
        return jep.getValue();
    }

    public static boolean isFormula(String exp) {
        JEP jep = parse(exp);
        return jep.getValueAsObject() != null;
    }
}
