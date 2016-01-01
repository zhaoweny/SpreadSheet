package spreadsheet.api;

import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;



/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public interface SpreadSheet {
    void setExpression(Location location, String expression);

    String getExpression(Location location);

    Value getValue(Location location);

    void reCompute();
}
