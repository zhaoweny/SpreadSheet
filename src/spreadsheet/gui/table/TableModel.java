package spreadsheet.gui.table;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
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

    public TableModel(SpreadSheet spreadSheet, int row, int column) {
        this.row = row;
        this.column = column;
        this.spreadSheet = spreadSheet;
    }

    @Override
    public int getRowCount() {
        return row;
    }

    @Override
    public int getColumnCount() {
        return column;
    }

    @Override
    public final String getColumnName(int index) {
        try{
            return Location.convertColumn(index);
        }catch (IllegalArgumentException e){
            return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > row)
            return null;
        if (columnIndex < 0 || columnIndex > column)
            return null;
        Location location = new Location(rowIndex, columnIndex);
        return spreadSheet.getValue(location);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Location location = new Location(rowIndex, columnIndex);
        if (aValue instanceof String) {
            String value = ((String) aValue).trim();

            spreadSheet.setExpression(location, value);
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

    public void fireTableDataChanged() {
        TableModelEvent event = null;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TableModelListener.class) {
                if (event == null)
                    event = new TableModelEvent(this);
                ((TableModelListener) listeners[i + 1]).tableChanged(event);
            }
        }
    }
}
