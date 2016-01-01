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
        assertEqualsOnConvertColumn(null, 0);
    }

    @Test
    public void testConvertColumnAppend() {
        assertEqualsOnConvertColumn("aa", 27);
        assertEqualsOnConvertColumn("ab", 28);
    }

    @Test
    public void testConvertColumnRandom() {
        assertEqualsOnConvertColumn("fq", 173);
        assertEqualsOnConvertColumn("abt", 748);
    }

    @Test
    public void testConvertColumnNormal() {
        assertEqualsOnConvertColumn("a", 1);
        assertEqualsOnConvertColumn("z", 26);
    }

    private void assertEqualsOnConvertColumn(String expected, int index) {
        Assert.assertEquals(expected, Location.convertColumn(index));
    }

    @Test
    public void testLocationConstructor() {
        Location target = new Location(1, 1);
        Location expect = new Location("a2");
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
