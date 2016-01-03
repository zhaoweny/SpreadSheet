package spreadsheet.test.implement.SpreadsheetImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.CellImpl;
import spreadsheet.implement.SpreadsheetImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by zhaow on 1/1/2016.
 * SimpleExcel
 */
public class TestSpreadSheetImpl extends SpreadSheetImplTestingTools {
    @Before
    public void setUp() throws Exception {
        spreadsheet = new SpreadsheetImpl();
    }

    @Test
    public void testInsertCellIfNotExistAtLocation() {
        Assert.assertNull(spreadsheet.getCellAt(a1));
        CellImpl cell = spreadsheet.InsertCellIfNotExistAt(a1);
        Assert.assertNotNull(cell);
        assertEquals(cell, spreadsheet.getCellAt(a1));
    }

    @Test
    public void testGetReferredLocation() {
        String exp = "a1=a2";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(a1);
        expect.add(a2);
        assertEquals(expect, target);
    }

    @Test
    public void testGetReferredLocationMultipleReference() {
        String exp = "a1=a2;a2=3";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(a1);
        expect.add(a2);
        assertEquals(expect, target);
    }

    @Test
    public void testGetReferredLocationLoopReference() {
        String exp = "a1=a1";
        Set<Location> target = SpreadsheetImpl.getReferredLocation(exp);
        Set<Location> expect = new HashSet<>();
        expect.add(a1);
        assertEquals(expect, target);
    }

    @Test
    public void testGetChildren() {
        CellImpl cell = new CellImpl(spreadsheet, null);
        cell.setExpression("a2+a3");
        Set<CellImpl> target = spreadsheet.getChildren(cell);
        Set<CellImpl> expect = new HashSet<>();
        expect.add(new CellImpl(spreadsheet, new Location("a2")));
        expect.add(new CellImpl(spreadsheet, new Location("a3")));
        assertEquals(expect, target);
    }

    @Test
    public void testGetAndSetExpression() {
        Location location = new Location(0, 0);
        String expression = "10";
        setExp(location, expression);
        assertEquals(expression, spreadsheet.getExpression(location));
    }

    @Test
    public void testGetAndSetExpressionWithReference() {
        String expression = "10*a2+9*a3+8*a4+a5+a6+a7+a6";
        setExp(a1, expression);
        assertEquals(expression, spreadsheet.getExpression(a1));
    }

    @Test
    public void testGetValue() throws Exception {
        assertNull(spreadsheet.getValue(new Location(0, 0)));
    }
}
