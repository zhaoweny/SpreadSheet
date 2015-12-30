package spreadsheet.gui.table;

import spreadsheet.api.SpreadSheet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class CellEditor extends DefaultCellEditor {
    private JTextField textField;
    private SpreadSheet spreadSheet;

    public CellEditor(JTextField textField, SpreadSheet spreadSheet) {
        super(textField);
        this.textField = textField;
        this.spreadSheet = spreadSheet;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
