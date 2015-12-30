package spreadsheet;

import spreadsheet.api.SpreadSheet;

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
}
