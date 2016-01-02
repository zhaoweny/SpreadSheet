package spreadsheet.test.api.cell;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.api.cell.Location;

import static org.junit.Assert.*;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class TestLocation {
    @Test
    public void testMaxLength() {
        assertEquals(26 * 27, Location.maxLength(2));
        assertEquals((int) (26 + Math.pow(26, 2) + Math.pow(26, 3)),
                Location.maxLength(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertColumnThrowsException() {
        assertEqualsOnConvertColumn(null, -1);
    }

    @Test
    public void testConvertColumnAppend() {
        assertEqualsOnConvertColumn("aa", 26);
        assertEqualsOnConvertColumn("ab", 27);
    }

    @Test
    public void testConvertColumnRandom() {
        assertEqualsOnConvertColumn("fq", 172);
        assertEqualsOnConvertColumn("abt", 747);
    }

    @Test
    public void testConvertColumnNormal() {
        assertEqualsOnConvertColumn("a", 0);
        assertEqualsOnConvertColumn("z", 25);
    }

    private void assertEqualsOnConvertColumn(String expected, int index) {
        assertEquals(expected, Location.convertColumn(index));
    }

    @Test
    public void testLocationConstructor() {
        Location target = new Location(1, 1);
        Location expect = new Location("b2");
        assertEquals(expect, target);
    }

    @Test
    public void testIsLocationValid() {
        Location target = new Location(1, 1);
        assertTrue(Location.isValidLocation(target));
    }

    @Test
    public void testIsLocationInvalid() {
        Location target = new Location("x0");
        assertFalse("target " + target.toString() + " should not be valid!",
                Location.isValidLocation(target));
    }

    @Test
    public void testEqualSameObject(){
        Location target = new Location(0,0);
        assertEquals(target,target);
    }
    @Test
    public void testEqualNotInstance(){
        Location target = new Location(0,0);
        assertNotEquals(target, new Object());
    }

    @Test(expected = NullPointerException.class)
    public void testNullStringConstructor(){
        new Location(null);
    }
}
