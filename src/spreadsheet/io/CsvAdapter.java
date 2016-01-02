package spreadsheet.io;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.ValueVisitor;
import spreadsheet.implement.CellImpl;
import spreadsheet.implement.SpreadsheetImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhaow on 1/1/2016.
 * SimpleExcel
 */
public class CsvAdapter {
    private static final String Write_Separator = "#";

    public static void read(Reader src, SpreadSheet spreadSheet) throws IOException {
        CSVReader reader = new CSVReader(src);

        int rowCount = 0;
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            int colCount = -1;

            for (String exp : nextLine) {
                colCount++;
                if (exp.equals("")) {
                    continue;
                }
                spreadSheet.setExpression(new Location(rowCount, colCount), exp.trim());
            }
            rowCount++;
        }

        reader.close();
    }

    public static void write(Writer tgt, SpreadsheetImpl spreadsheet, int maxRow, int maxCol) throws IOException {
        CSVWriter writer = new CSVWriter(tgt);

        for (int i = 0; i < maxRow; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < maxCol; j++) {
                Location location = new Location(i,j);
                CellImpl cell = spreadsheet.getCellAt(location);
                if (cell!=null) {
                    cell.getValue().visit(new ValueVisitor() {
                        @Override
                        public void visitLoop() {

                        }

                        @Override
                        public void visitInvalid(String exp) {
                            builder.append(exp);
                        }

                        @Override
                        public void visitNumber(double val) {
                            builder.append(Double.toString(val));
                        }

                        @Override
                        public void visitString(String exp) {
                            builder.append(exp);
                        }
                    });
                }
                builder.append(Write_Separator);
            }
            builder.deleteCharAt(builder.lastIndexOf(Write_Separator));
            List<String> list = new ArrayList<>();
            Collections.addAll(list, builder.toString().split(Write_Separator));
            if (builder.lastIndexOf(Write_Separator)==builder.length()-1)
                list.add("");
            String[] pending = new String[list.size()];
            list.toArray(pending);
            writer.writeNext(pending);
        }
        writer.close();
    }
}
