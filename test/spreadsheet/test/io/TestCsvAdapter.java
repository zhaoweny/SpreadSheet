package spreadsheet.test.io;

import org.junit.Assert;
import org.junit.Test;
import spreadsheet.api.cell.Location;
import spreadsheet.implement.SpreadsheetImpl;
import spreadsheet.io.CsvAdapter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by zhaow on 1/2/2016.
 * SimpleExcel
 */
public class TestCsvAdapter {
    @Test
    public void TestCsvAdapterWrite() throws IOException {
        SpreadsheetImpl src = new SpreadsheetImpl();
        src.setExpression(new Location("a1"), "1");
        src.setExpression(new Location("a2"), "2");
        src.setExpression(new Location("b1"), "3");
        src.setExpression(new Location("b2"), "4");
        String expect = "\"1\",\"3\"\n\"2\",\"4\"\n";
        StringWriter writer = new StringWriter();
        CsvAdapter.write(writer, src, 2, 2);
        Assert.assertEquals(expect, writer.toString());
    }

    @Test
    public void TestCsvAdapterRead() throws IOException {
        String src = "\"1\",\"3\"\n\"2\",\"4\"\n";
        Assert.assertEquals(src, readAndWrite(src, 2, 2, false));
    }

    private static String readAndWrite(String src, int row, int col, boolean calc) throws IOException {
        SpreadsheetImpl target = new SpreadsheetImpl();
        CsvAdapter.read(new StringReader(src), target);

        if (calc)
            target.reCompute();

        StringWriter writer = new StringWriter();
        CsvAdapter.write(writer, target, row, col);

        return writer.toString();
    }

    @Test
    public void TestCsvAdapterReadRecomputeAndWrite() throws IOException {
        String src = "\"1\",\"a\"\n\"2\",\"b\"\n";
        Assert.assertEquals(src, readAndWrite(src, 2, 2, true));
    }

    @Test
    public void TestCsvAdapterNotFull() throws IOException {
        String src = "\"1\",\"\",\"3\"\n\"4\",\"5\",\"\"\n\"\",\"8\",\"9\"\n";
        Assert.assertEquals(src, readAndWrite(src, 3, 3, false));

    }
}
