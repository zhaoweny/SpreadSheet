package spreadsheet.test.implement.SpreadsheetImpl;

import org.junit.Before;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.SpreadsheetImpl;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class TestCheckLoop extends SpreadSheetImplTestingTools {
    @Before
    public void setUp() throws Exception {
        spreadsheet = new SpreadsheetImpl();
    }

    @Test
    public void testNoReference() throws Exception {
        setExp(a1, "1");

        chkLoop(a1);
        assertNotLoop(a1);
    }

    @Test
    public void testNormalReference() throws Exception {
        setExp(a1, "a2");
        setExp(a2, "a3");
        setExp(a3, "1.0");

        chkLoop(a1);
        assertNotLoop(a1);
        chkLoop(a2);
        assertNotLoop(a2);
    }

    @Test
    public void testComplexReference() throws Exception {
        setExp(a1, "a2+a3");
        setExp(a2, "a4+a5");
        setExp(a3, "a2+a4+a5");
        setExp(new Location("a4"), "0.1");
        setExp(new Location("a5"), "0.2");

        chkLoop(a1);
        assertNotLoop(a1);
        chkLoop(a2);
        assertNotLoop(a2);
        chkLoop(a3);
        assertNotLoop(a3);
    }

    @Test
    public void testDirectLoopReference() throws Exception {
        setExp(a1, "a1");
        chkLoop(a1);
        assertIsLoop(a1);
    }

    @Test
    public void testCircleLoopReference() throws Exception {
        setExp(a1, "a2");
        setExp(a2, "a3");
        setExp(a3, "a1");
        chkLoop(a1);
        assertIsLoop(a1);
    }
    void chkLoop(Location loc) {
        spreadsheet.checkLoops(spreadsheet.getCellAt(loc), null);
    }

}
