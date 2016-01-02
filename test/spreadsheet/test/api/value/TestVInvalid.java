package spreadsheet.test.api.value;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.api.value.vInvalid;

/**
 * Created by zhaow on 1/2/2016.
 * SimpleExcel
 */
public class TestVInvalid {
    @Test
    public void testToString() throws Exception {
        vInvalid value = new vInvalid("foobar");
        Assert.assertEquals("{foobar}",value.toString());
    }
}
