package spreadsheet.implement;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.Value;
import spreadsheet.api.value.vInvalid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class SpreadsheetImpl implements SpreadSheet {
    private Map<Location, CellImpl> locationCellMap = new HashMap<>();
    private Set<CellImpl> modified = new HashSet<>();
    private Set<CellImpl> ignore = new HashSet<>();

    public CellImpl getCellAt(Location location) {
        CellImpl cell = locationCellMap.get(location);
        if (cell == null) {
            cell = new CellImpl(this, location);
            cell.setValue(null);
            locationCellMap.put(location, cell);
        }
        return cell;
    }

    public static Set<Location> getReferredLocation(String exp) {
        Set<Location> result = new HashSet<>();
        Pattern p = Pattern.compile("[a-zA-z]+[1-9]\\d*");
        Matcher m = p.matcher(exp);
        while (m.find()) {
            String location = exp.substring(m.start(), m.end());
            result.add(new Location(location));
        }
        return result;
    }

    public Set<CellImpl> getChildren(CellImpl cell) {
        Set<Location> childrenLocation = getReferredLocation(cell.getExpression());
        return childrenLocation.stream().map(this::getCellAt).collect(Collectors.toSet());
    }

    @Override
    public void setExpression(Location location, String expression) {
        CellImpl cell = getCellAt(location);
        if (!modified.contains(cell) || cell.getExpression().equals(expression))
            cell.setExpression(expression);
        cell.setValue(new vInvalid(expression));
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

    public Set<CellImpl> getModified() {
        return modified;
    }
}
