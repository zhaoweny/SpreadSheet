package spreadsheet.test.parser;

import org.junit.Test;
import org.nfunk.jep.ParseException;
import spreadsheet.parser.Parser;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class TestParseCommand {
    private static List<String[]> parseCmd(String target) throws ParseException {
        return Parser.parseCommand(target);
    }

    private static void assertStringEquals(String[][] expect, String[][] target) {
        if (expect.length != target.length)
            fail("String[] length doesn't match.");
        for (int i = 0; i < expect.length; i++) {
            if (expect[i].length != target[i].length)
                fail("String length doesn't match.");
            for (int j = 0; j < expect[i].length; j++) {
                assertEquals(expect[i][j], target[i][j]);
            }
        }
    }

    @Test
    public void testSingleCommand() throws Exception {
        String target = "a1 = 1";
        String[][] strings = parseCmd(target).toArray(new String[1][1]);
        assertEquals("a1", strings[0][0]);
        assertEquals("1", strings[0][1]);
    }

    @Test
    public void testMultipleCommand() throws Exception {
        String target = "a1 =1;a2 = 2";
        String[][] results = parseCmd(target).toArray(new String[1][1]);
        String[][] expect = {{"a1", "1"}, {"a2", "2"}};
        assertStringEquals(expect, results);
    }

    @Test(expected = ParseException.class)
    public void testInvalidCommand() throws Exception {
        String target = "a0=a1";
        parseCmd(target);

    }
}
