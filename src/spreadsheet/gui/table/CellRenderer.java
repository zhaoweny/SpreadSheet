package spreadsheet.gui.table;

import spreadsheet.api.SpreadSheet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class CellRenderer extends DefaultTableCellRenderer{
    private SpreadSheet spreadSheet;
    public CellRenderer(SpreadSheet spreadSheet){
        super();
        this.spreadSheet = spreadSheet;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
