package spreadsheet.parser;

import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;
import spreadsheet.api.cell.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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

    public static List<String[]> parseCommand(String cmd) throws ParseException {
        List<String[]> result = new ArrayList<>();
        StringTokenizer stStatement = new StringTokenizer(cmd, ";");
        while (stStatement.hasMoreTokens()) {
            StringTokenizer stAssignment = new StringTokenizer(stStatement.nextToken(), "=");
            while (stAssignment.hasMoreTokens()) {
                String left = stAssignment.nextToken().trim();
                String right = stAssignment.nextToken().trim();

                if (Location.isValidLocation(left)) {
                    result.add(new String[]{left, right});
                } else {
                    throw new ParseException("Invalid Command: " + left + "=" + right);
                }
            }

        }
        return result;
    }

    public Parser parse(String exp) {
        if (exp.startsWith("="))
            exp = exp.replaceFirst("=", "");
        jep.parseExpression(exp);
        return this;
    }

    public double getDouble() {
        return jep.getValue();
    }

    public boolean isValid() {
        return jep.getValueAsObject() != null;
    }

    public void addVariable(String variable, double value) {
        jep.addVariable(variable, value);
    }

    public void addVariableAsObject(String variable, String string) {
        jep.addVariableAsObject(variable, string);
    }

    public void addValueMap(Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet())
            addVariable(entry.getKey(), entry.getValue());
    }

    public void addStringMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet())
            addVariableAsObject(entry.getKey(), entry.getValue());
    }
}
