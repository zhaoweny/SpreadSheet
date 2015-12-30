package spreadsheet.parser;

import org.nfunk.jep.JEP;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class Parser {
    JEP parser;
    public Parser(){
        parser = new JEP();
    }

    public void parse(String exp){
        preProcess(exp);
    }

    private String preProcess(String exp) {
        return null;
    }
}
