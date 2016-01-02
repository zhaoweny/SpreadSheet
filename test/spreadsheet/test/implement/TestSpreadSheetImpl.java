package spreadsheet.test.implement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;
import spreadsheet.api.value.ValueVisitor;
import spreadsheet.implement.CellImpl;
import spreadsheet.implement.SpreadsheetImpl;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by zhaow on 1/1/2016.
 * SimpleExcel
 */
public class TestSpreadSheetImpl {
    SpreadsheetImpl spreadsheet;

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
        spreadsheet.setExpression(location, expression);
        assertEquals(expression, spreadsheet.getExpression(location));
    }

    @Test
    public void testGetAndSetExpressionWithReference() {
        String expression = "10*a2+9*a3+8*a4+a5+a6+a7+a6";
        spreadsheet.setExpression(a1, expression);
        assertEquals(expression, spreadsheet.getExpression(a1));
    }

    @Test
    public void testGetValue() throws Exception {
        assertNull(spreadsheet.getValue(new Location(0, 0)));
    }

    private final static Location a1 = new Location("a1");
    private final static Location a2 = new Location("a2");
    private final static Location a3 = new Location("a3");

    @Test
    public void testReComputeNoReference() throws Exception {
        spreadsheet.setExpression(a1, "1");
        spreadsheet.setExpression(a2, "1");
        spreadsheet.reCompute();

        assertIsNumber(spreadsheet.getValue(a1), 1);
        assertIsNumber(spreadsheet.getValue(a2), 1);
    }

    @Test
    public void testReComputeSimpleReference() throws Exception {
        spreadsheet.setExpression(a1, "a2");
        spreadsheet.setExpression(a2, "1");
        spreadsheet.reCompute();

        assertIsNumber(spreadsheet.getValue(a1), 1);
        assertIsNumber(spreadsheet.getValue(a2), 1);
    }

    @Test
    public void testReComputeLoopReference() throws Exception {
        spreadsheet.setExpression(a1, "a2");
        spreadsheet.setExpression(a2, "a3");
        spreadsheet.setExpression(a3, "a1");
        spreadsheet.reCompute();
        assertIsLoop(spreadsheet.getValue(a1));
        assertIsLoop(spreadsheet.getValue(a2));
        assertIsLoop(spreadsheet.getValue(a3));
    }

    @Test
    public void testReComputeStringReference() throws Exception {
        spreadsheet.setExpression(a1, "foobar");
        spreadsheet.setExpression(a2, "a1");
        spreadsheet.reCompute();
        assertIsString(spreadsheet.getValue(a1), "foobar");
        assertIsString(spreadsheet.getValue(a2), "a1");
    }

    @Test
    public void testReComputeChainedReference() throws Exception {
        spreadsheet.setExpression(a1, "a2");
        spreadsheet.setExpression(a2, "a3");
        spreadsheet.setExpression(a3, "1.0");
        spreadsheet.reCompute();
        assertIsNumber(spreadsheet.getValue(a1), 1);
        assertIsNumber(spreadsheet.getValue(a2), 1);
        assertIsNumber(spreadsheet.getValue(a3), 1);
    }

    //@Test
    public void testReComputeComplexReference() throws Exception {
        spreadsheet.setExpression(a1, "a2+a3");
        spreadsheet.setExpression(a2, "a4+a5");
        spreadsheet.setExpression(a3, "a2+a4+a5");
        spreadsheet.setExpression(new Location("a4"), "0.1");
        spreadsheet.setExpression(new Location("a5"), "0.2");

        spreadsheet.reCompute();

        assertIsNumber(spreadsheet.getValue(a1), 0.9);
        assertIsNumber(spreadsheet.getValue(a2), 0.3);
        assertIsNumber(spreadsheet.getValue(a3), 0.6);
    }

    private static void assertIsLoop(Value target) {
        String prefix = "assertIsLoop: ";
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                // Yay!
            }

            @Override
            public void visitInvalid(String exp) {
                fail(prefix + "got Invalid " + exp);
            }

            @Override
            public void visitNumber(double val) {
                fail(prefix + "got Number " + val);
            }

            @Override
            public void visitString(String exp) {
                fail(prefix + "got String " + exp);
            }
        });
    }

    private static void assertIsInvalid(Value target, String expect) {
        String prefix = String.format("assertIsInvalid: %s ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, expect, exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, Double.parseDouble(expect) == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, expect, exp);
            }
        });
    }

    private static void assertIsNumber(Value target, double expect) {
        String prefix = String.format("assertIsNumber: %f ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop.");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, Double.toString(expect), exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, expect == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, Double.toString(expect), exp);
            }
        });
    }

    private static void assertIsString(Value target, String expect) {
        String prefix = String.format("assertIsString: %s ", expect);
        target.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {
                fail(prefix + "got Loop.");
            }

            @Override
            public void visitInvalid(String exp) {
                assertEquals(prefix + "ne Invalid " + exp, expect, exp);
            }

            @Override
            public void visitNumber(double val) {
                assertTrue(prefix + "ne Number " + val, Double.parseDouble(expect) == val);
            }

            @Override
            public void visitString(String exp) {
                assertEquals(prefix + "ne String " + exp, expect, exp);
            }
        });
    }
}
