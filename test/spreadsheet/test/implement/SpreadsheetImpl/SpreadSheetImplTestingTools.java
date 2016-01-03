package spreadsheet.test.implement.SpreadsheetImpl;

import org.junit.Before;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.vLoop;
import spreadsheet.implement.SpreadsheetImpl;
import spreadsheet.test.api.value.ValueAsserts;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by zhaow on 1/4/2016.
 * SimpleExcel
 */
public class SpreadSheetImplTestingTools {
     final static Location a1 = new Location("a1");
     final static Location a2 = new Location("a2");
     final static Location a3 = new Location("a3");
     SpreadsheetImpl spreadsheet;

     void setExp(Location loc, String exp) {
        spreadsheet.setExpression(loc, exp);
    }



     void assertIsLoop(Location loc) {
        ValueAsserts.assertIsLoop(spreadsheet.getValue(loc));
    }

     void assertNotLoop(Location loc) {
        assertNotEquals(spreadsheet.getValue(loc), vLoop.INSTANCE);
    }
     void assertEqualsNumber(Location location, int expect) {
        ValueAsserts.assertEqualsNumber(spreadsheet.getValue(location), expect);
    }



}
