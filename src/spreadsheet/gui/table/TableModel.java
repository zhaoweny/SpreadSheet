package spreadsheet.gui.table;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class TableModel implements javax.swing.table.TableModel {
    private final int row;
    private final int column;
    private SpreadSheet spreadSheet;

    private EventListenerList listenerList = new EventListenerList();

    public TableModel(int row, int column, SpreadSheet spreadSheet) {
        this.row = row;
        this.column = column;
        this.spreadSheet = spreadSheet;
    }

    public static String convertColumn(int index) {
        if (index <= 0) {
            return null;
        }

        index--;

        int n = 0;
        while (index >= maxLength(n))
            ++n;

        int m = index - maxLength(n - 1);

        StringBuilder sb = new StringBuilder();
        // converts
        for (int j = n; j > 0; --j) {
            int v = (m % (int) Math.pow(26, j)) / (int) Math.pow(26, j - 1);
            sb.append((char) ('a' + v));
        }

        return sb.toString();
    }

    // sum of BASE to the power 1 to input
    protected static int maxLength(int input) {
        return (int) (Math.pow(26, input) - 1) * 26 / (26 - 1);
    }


    @Override
    public int getRowCount() {
        return row + 1;
    }

    @Override
    public int getColumnCount() {
        return column;
    }

    @Override
    public final String getColumnName(int index) {
        return convertColumn(index);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return column != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > row)
            return null;
        if (columnIndex <= 0 || columnIndex > column)
            return null;
        Location location = new Location(getColumnName(columnIndex) + (rowIndex + 1));
        return spreadSheet.getValue(location);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Location location = new Location(getColumnName(columnIndex) + (rowIndex + 1));
        if (aValue instanceof String) {
            String value = ((String) aValue).trim();
            // TODO: not finished yet.
        }

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);

    }


}
