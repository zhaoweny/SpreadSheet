package spreadsheet.test.implement;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.CellImpl;
import spreadsheet.implement.SpreadsheetImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhaow on 1/1/2016.
 * SimpleExcel
 */
public class TestSpreadSheetImpl {
    @Test
    public void testGetCellAt() {
        Location location = new Location(1, 1);
        SpreadsheetImpl spreadsheet = new SpreadsheetImpl();
        CellImpl cell = spreadsheet.InsertCellIfNotExistAt(location);
        Assert.assertNotNull(cell);
        Assert.assertEquals(cell, spreadsheet.InsertCellIfNotExistAt(location));
    }

    @Test
    public void testGetReferredLocation() {
        String exp = "a1=a2";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(new Location("a1"));
        expect.add(new Location("a2"));
        Assert.assertEquals(expect, target);
    }

    @Test
    public void testGetReferredLocationMultipleReference() {
        String exp = "a1=a2;a2=3";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(new Location("a1"));
        expect.add(new Location("a2"));
        Assert.assertEquals(expect, target);
    }

    @Test
    public void testGetReferredLocationLoopReference() {
        String exp = "a1=a1";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(new Location("a1"));
        Assert.assertEquals(expect, target);
    }

    @Test
    public void testGetChildren() {
        SpreadsheetImpl spreadsheet = new SpreadsheetImpl();
        CellImpl cell = new CellImpl(spreadsheet, null);
        cell.setExpression("a2+a3");
        Set<CellImpl> target = spreadsheet.getChildren(cell);
        Set<CellImpl> expect = new HashSet<>();
        expect.add(new CellImpl(spreadsheet, new Location("a2")));
        expect.add(new CellImpl(spreadsheet, new Location("a3")));
        Assert.assertEquals(expect, target);
    }
}
