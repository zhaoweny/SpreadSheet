package spreadsheet.parser;

import org.nfunk.jep.JEP;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class Parser {
    JEP jep = new JEP();
    public Parser() {
        jep.addStandardConstants();
        jep.addStandardFunctions();
    }

    public Parser parse(String exp){
        jep.parseExpression(exp);
        return this;
    }

    public double getDouble() {
        return jep.getValue();
    }

    public boolean isValid() {
        return jep.getValueAsObject() != null;
    }

    public void addVariable(String variable, double value){
        jep.addVariable(variable,value);
    }
}
