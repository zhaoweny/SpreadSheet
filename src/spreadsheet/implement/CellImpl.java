package spreadsheet.implement;

import spreadsheet.api.cell.Cell;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;
import spreadsheet.api.value.vInvalid;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhaow on 12/31/2015.
 * SimpleExcel
 */
public class CellImpl implements Cell {
    private final SpreadsheetImpl spreadSheet;
    private final Location location;
    private Value value;
    private String expression;
    private boolean isModified;

    private Set<CellImpl> thisRefer = new HashSet<>();
    private Set<Cell> referThis = new HashSet<>();

    public CellImpl(SpreadsheetImpl spreadSheet, Location location) {
        this.spreadSheet = spreadSheet;
        this.location = location;
        this.value = null;
        this.expression = "";
        this.isModified = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellImpl cell = (CellImpl) o;

        if (isModified != cell.isModified) return false;
        if (spreadSheet != null ? !spreadSheet.equals(cell.spreadSheet) : cell.spreadSheet != null) return false;
        if (location != null ? !location.equals(cell.location) : cell.location != null) return false;
        if (expression != null ? !expression.equals(cell.expression) : cell.expression != null) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = spreadSheet != null ? spreadSheet.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        result = 31 * result + (isModified ? 1 : 0);
        return result;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        if (expression.startsWith("="))
            expression = expression.replaceFirst("=", "");
        for (CellImpl cell : thisRefer) {
            cell.referThis.remove(this);
        }
        this.thisRefer.clear();

        this.expression = expression;

        setValue(new vInvalid(expression));
        spreadSheet.getModified().add(this);

        for (Location location : SpreadsheetImpl.getReferredLocation(expression)) {
            CellImpl cell = spreadSheet.InsertCellIfNotExistAt(location);
            thisRefer.add(cell);
            cell.referThis.add(this);
        }

        for (Cell cell : referThis)
            cell.update(this);
    }

    @Override
    public void update(Cell changed) {
        if (!spreadSheet.getModified().contains(this)) {
            spreadSheet.getModified().add(this);
            setValue(new vInvalid(expression));

            for (Cell cell : referThis)
                cell.update(this);
        }
    }

    @Override
    public String toString() {
        return String.format("%s = %s", location, expression);
    }

    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isModified() {
        return isModified;
    }
}
