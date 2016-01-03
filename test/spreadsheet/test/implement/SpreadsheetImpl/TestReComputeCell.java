package spreadsheet.test.implement.SpreadsheetImpl;

import org.junit.Before;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.SpreadsheetImpl;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class TestReComputeCell extends SpreadSheetImplTestingTools {
    @Before
    public void setUp() throws Exception {
        spreadsheet = new SpreadsheetImpl();
    }

    @Test
    public void testNoReference() throws Exception {
        setExp(a1, "1");
        reComputeCellAt(a1);
        assertEqualsNumber(a1, 1);
    }

    @Test
    public void testChainedReference() throws Exception {
        setExp(a1, "a2");
        setExp(a2, "a3");
        setExp(a3, "1");
        reComputeCellAt(a1);
        assertEqualsNumber(a1, 1);
    }

    @Test
    public void testComplexReference() throws Exception {
        setExp(a1, "a2+a3");
        setExp(a2, "a4+a5");
        setExp(a3, "a2+a4+a5");
        setExp(new Location("a4"), "1");
        setExp(new Location("a5"), "2");
        reComputeCellAt(a1);
        assertEqualsNumber(a1, 9);
    }

    void reComputeCellAt(Location loc) {
        spreadsheet.reComputeCell(spreadsheet.getCellAt(loc));
    }
}
