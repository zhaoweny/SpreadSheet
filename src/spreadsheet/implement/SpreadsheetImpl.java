package spreadsheet.implement;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class SpreadsheetImpl implements SpreadSheet{

    private final int colCount;
    private final int rowCount;

    public SpreadsheetImpl(int rowCount, int colCount){
        this.rowCount = rowCount;
        this.colCount = colCount;

    }


    @Override
    public void setExpression(Location location, String expression) {

    }

    @Override
    public String getExpression(Location location) {
        return null;
    }

    @Override
    public Value getValue(Location location) {
        return null;
    }

    @Override
    public void reCompute() {

    }
}
