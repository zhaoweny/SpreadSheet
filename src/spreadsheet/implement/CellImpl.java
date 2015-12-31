package spreadsheet.implement;

import spreadsheet.api.cell.Cell;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;

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

    private Set<CellImpl> referTo = new HashSet<>();
    private Set<Cell> referFrom = new HashSet<>();

    public CellImpl(SpreadsheetImpl spreadSheet, Location location){
        this.spreadSheet = spreadSheet;
        this.location = location;
        this.value = null;
        this.expression = "";
        this.isModified = false;
    }

    public Set<CellImpl> getReferTo() {
        return referTo;
    }

    public void setReferTo(Set<CellImpl> referTo) {
        this.referTo = referTo;
    }

    public Set<Cell> getReferFrom() {
        return referFrom;
    }

    public void setReferFrom(Set<Cell> referFrom) {
        this.referFrom = referFrom;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public Location getLocation() {
        return location;
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
        this.expression = expression;
    }

    @Override
    public void update(Cell changed) {

    }
}
