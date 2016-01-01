package spreadsheet.test.api.cell;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.api.cell.Location;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class TestLocation {
    @Test
    public void testMaxLength() {
        Assert.assertEquals(26 * 27, Location.maxLength(2));
        Assert.assertEquals((int) (26 + Math.pow(26, 2) + Math.pow(26, 3)),
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
        Assert.assertEquals(expected, Location.convertColumn(index));
    }

    @Test
    public void testLocationConstructor() {
        Location target = new Location(1, 1);
        Location expect = new Location("b2");
        Assert.assertEquals(expect, target);
    }

    @Test
    public void testIsLocationValid() {
        Location target = new Location(1, 1);
        Assert.assertTrue(Location.isValidLocation(target));
    }

    @Test
    public void testIsLocationInvalid() {
        Location target = new Location("x0");
        Assert.assertFalse("target " + target.toString() + " should not be valid!",
                Location.isValidLocation(target));
    }
}
