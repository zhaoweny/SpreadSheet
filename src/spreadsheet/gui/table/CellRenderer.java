package spreadsheet.gui.table;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;
import spreadsheet.api.value.ValueVisitor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class CellRenderer extends DefaultTableCellRenderer {
    private static Border focusCellHighlightBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
    private static Color focusCellForeground = UIManager.getColor("Table.focusCellForeground");
    private static Color focusCellBackground = UIManager.getColor("Table.focusCellBackground");
    private static Color headerBackground = UIManager.getColor("TableHeader.background");
    private static Color tableBackground = UIManager.getColor("Table.background");

    private SpreadSheet spreadSheet;

    public CellRenderer(SpreadSheet spreadSheet) {
        super();
        this.spreadSheet = spreadSheet;

        checkColorSettings();
    }

    private static void checkColorSettings() {
        if (focusCellHighlightBorder == null)
            focusCellHighlightBorder = new LineBorder(Color.GRAY, 2);

        if (focusCellForeground == null)
            focusCellForeground = Color.WHITE;

        if (focusCellBackground == null)
            focusCellBackground = Color.WHITE;

        if (headerBackground == null)
            headerBackground = new Color(238, 238, 238);

        if (tableBackground == null)
            tableBackground = Color.WHITE;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // general condition, set no border and data background
        setBorder(noFocusBorder);
        setBackground(tableBackground);

//        // row header, set value and header background
//        if (column == 0) {
//            value = String.valueOf(row + 1);
//            setBackground(headerBackground);
//            setHorizontalAlignment(JTextField.CENTER);
//            setValue(value);
//            return this;
//        }

        // data table, set right Horizon Alignment
        Location location = new Location(row, column);
        Value v = spreadSheet.getValue(location);
        if (v != null) {
            v.visit(new ValueVisitor() {
                @Override
                public void visitLoop() {
                    setHorizontalAlignment(JTextField.CENTER);
                }

                @Override
                public void visitInvalid(String exp) {
                    setHorizontalAlignment(JTextField.LEFT);
                }

                @Override
                public void visitNumber(double val) {
                    setHorizontalAlignment(JTextField.RIGHT);
                }

                @Override
                public void visitString(String exp) {
                    setHorizontalAlignment(JTextField.LEFT);
                }
            });
        }

        // has focus, set focus border and focus background and foreground
        if (hasFocus) {
            setBorder(focusCellHighlightBorder);
            setForeground(focusCellForeground);
            setBackground(focusCellBackground);
        }

        setValue(value);
        return this;
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue,
                                      Object newValue) {
    }

    @Override
    public void invalidate() {
    }

    @Override
    public void repaint() {
    }
}
