package spreadsheet.test.api.value;

import org.junit.Test;
import spreadsheet.api.value.Value;
import spreadsheet.api.value.ValueVisitor;
import spreadsheet.api.value.vDouble;

import static org.junit.Assert.*;

/**
 * Created by zhaow on 1/2/2016.
 * SimpleExcel
 */
public class TestVDouble {
    @Test
    public void TestVisit() {
        Value value = new vDouble(1.0);
        value.visit(new ValueVisitor() {
            @Override
            public void visitLoop() {

            }

            @Override
            public void visitInvalid(String exp) {

            }

            @Override
            public void visitNumber(double val) {
                assertTrue(val == 1.0);
            }

            @Override
            public void visitString(String exp) {

            }
        });
    }

    @Test
    public void TestToString() {
        vDouble value = new vDouble(1.0);
        assertEquals(Double.toString(1.0), value.toString());
    }

    @Test
    public void TestEqualsSameObject() {
        vDouble target = new vDouble(1.0);
        assertEquals(target, target);
    }

    @Test
    public void TestEqualsSameValue() {
        vDouble target = new vDouble(1.0);
        vDouble expect = new vDouble(1.0);
        assertEquals(expect, target);
    }

    @Test
    public void TestEqualsNullObject(){
        //noinspection ObjectEqualsNull
        assertFalse(new vDouble(1.0).equals(null));
    }

    @Test
    public void testEqualsNotSameClass() throws Exception {
        assertFalse(new vDouble(1.0).equals(new Object()));
    }

    @Test
    public void testEqualsNotSameValue() throws Exception {
        assertNotEquals(new vDouble(1.0),new vDouble(2.0));
    }

    @Test
    public void testHashCodeSameValue() throws Exception {
        assertEquals(new vDouble(1.0).hashCode(),new vDouble(1.0).hashCode());
    }

    @Test
    public void testHashCodeNotSameValue() throws Exception {
        assertNotEquals(new vDouble(1.0).hashCode(), new vDouble(2.0).hashCode());
    }
}
