package spreadsheet.test.implement.SpreadsheetImpl;

import org.junit.Before;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.SpreadsheetImpl;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class TestReCompute extends SpreadSheetImplTestingTools {
    @Before
    public void setUp() throws Exception {
        spreadsheet = new SpreadsheetImpl();
    }

    @Test
    public void testSimpleCell() throws Exception {
        setExp(a1, "1");
        reCompute();
        assertEqualsNumber(a1, 1);
    }

    @Test
    public void testChainedReference() throws Exception {
        setExp(a1, "a2");
        setExp(a2, "a3");
        setExp(a3, "1.0");
        reCompute();
        assertEqualsNumber(a1, 1);
        assertEqualsNumber(a2, 1);
        assertEqualsNumber(a3, 1);
    }

    @Test
    public void testComplexReference() throws Exception {
        setExp(a1, "a2+a3");
        setExp(a2, "a4+a5");
        setExp(a3, "a2+a4+a5");
        setExp(new Location("a4"), "1");
        setExp(new Location("a5"), "2");
        reCompute();
        assertEqualsNumber(a1, 9);
        assertEqualsNumber(a2,3);
        assertEqualsNumber(a3,6);
    }

    @Test
    public void testLoopReference() throws Exception {
        setExp(a1, "a2");
        setExp(a2, "a1");
        reCompute();
        assertIsLoop(a1);
        assertIsLoop(a2);

    }

    private void reCompute() {
        spreadsheet.reCompute();
    }
}
