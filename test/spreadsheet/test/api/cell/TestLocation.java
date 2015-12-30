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
        Assert.assertEquals((int)(26 + Math.pow(26, 2) + Math.pow(26, 3)),
                Location.maxLength(3));
    }

    @Test
    public void testConvertColumn() {
        //assert null is actual null
        assertEqualsOnConvertColumn(null, -1);
        assertEqualsOnConvertColumn(null, 0);

        // assert normal
        assertEqualsOnConvertColumn("a", 1);
        assertEqualsOnConvertColumn("z", 26);

        // assert append
        assertEqualsOnConvertColumn("aa", 27);
        assertEqualsOnConvertColumn("ab", 28);

        // assert random
        assertEqualsOnConvertColumn("fq", 173);
        assertEqualsOnConvertColumn("abt", 748);
    }

    private void assertEqualsOnConvertColumn(String expected, int index) {
        Assert.assertEquals(expected, Location.convertColumn(index));
    }
}
