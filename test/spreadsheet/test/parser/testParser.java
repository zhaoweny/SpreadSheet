package spreadsheet.test.parser;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.parser.Parser;

/**
 * Created by zhaow on 1/1/2016.
 * SimpleExcel
 */
public class testParser {
    private static boolean isValid(String exp) {
        return new Parser().parse(exp).isValid();
    }

    private static boolean isValid(Parser parser, String exp) {
        return parser.parse(exp).isValid();
    }

    @Test
    public void testInvalidFormula() {
        Assert.assertFalse(isValid("*#6969#*"));
        Assert.assertFalse(isValid("abc123"));
        Assert.assertFalse(isValid("23abc23"));
        Assert.assertFalse(isValid("foo is bar."));
    }

    @Test
    public void testSetVariables() {
        Parser parser = new Parser();
        parser.addVariable("a1", 2.0);
        parser.addVariable("ab23", 3.0);
        String expression = "=a1+ab23";
        Assert.assertTrue(isValid(parser, expression));
    }

    @Test
    public void testValidFormula() throws Exception {
        Parser parser = new Parser();
        parser.addVariable("a1", 2.0);
        parser.addVariable("ab23", 3.0);
        String expression = "a1+ab23";
        Assert.assertTrue(5 == parser.parse(expression).getDouble());
    }
}
