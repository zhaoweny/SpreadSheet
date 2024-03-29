package spreadsheet.implement;

import spreadsheet.api.SpreadSheet;
import spreadsheet.api.cell.Location;
import spreadsheet.api.value.*;
import spreadsheet.parser.Parser;

import java.util.*;
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

    public CellImpl InsertCellIfNotExistAt(Location location) {
        CellImpl cell = locationCellMap.get(location);
        if (cell == null) {
            cell = new CellImpl(this, location);
            cell.setValue(null);
            locationCellMap.put(location, cell);
        }
        return cell;
    }

    public CellImpl getCellAt(Location location) {
        return locationCellMap.get(location);
    }

    public Set<CellImpl> getChildren(CellImpl cell) {
        Set<Location> childrenLocation = getReferredLocation(cell.getExpression());
        return childrenLocation.stream().map(this::InsertCellIfNotExistAt).collect(Collectors.toSet());
    }

    @Override
    public void setExpression(Location location, String expression) {
        CellImpl cell = InsertCellIfNotExistAt(location);
        if (!modified.contains(cell) || !cell.getExpression().equals(expression))
            cell.setExpression(expression);
        cell.setValue(new vInvalid(expression));
    }

    @Override
    public String getExpression(Location location) {
        return InsertCellIfNotExistAt(location).getExpression();
    }

    @Override
    public Value getValue(Location location) {
        return InsertCellIfNotExistAt(location).getValue();
    }

    @Override
    public void reCompute() {
        modified.addAll(locationCellMap.entrySet().stream().
                filter(entry -> entry.getValue().getValue() instanceof vInvalid).
                map(Map.Entry::getValue).collect(Collectors.toList()));

        Iterator<CellImpl> iterator = modified.iterator();
        while (iterator.hasNext()) {
            CellImpl cell = iterator.next();

            cell.setModified(false);
            checkLoops(cell, null);

            if (cell.getValue() != vLoop.INSTANCE) {
                if (cell.isModified()) {
                    cell.setValue(new vInvalid(cell.getExpression()));
                } else if (!ignore.contains(cell)) {
                    reComputeCell(cell);
                }
            }
            iterator.remove();
        }
        ignore.clear();
    }

    public void reComputeCell(CellImpl target) {
        Deque<CellImpl> deque = new ArrayDeque<>();
        deque.offer(target);

        while (!deque.isEmpty()) {
            CellImpl current = deque.poll();
            boolean computeReference = false;
            for (CellImpl child : getChildren(current)) {
                if (modified.contains(child) &&
                        !ignore.contains(child)) {
                    deque.offer(child);
                    computeReference = true;
                }
            }
            deque.offer(current);
            if (!computeReference) {
                current.setValue(calculateValueOf(current));
                ignore.add(current);
                deque.remove(current);
            }
        }
    }

    public Value calculateValueOf(CellImpl target) {
        Map<String, Double> valueMap = new HashMap<>();
        Map<String, String> stringMap = new HashMap<>();

        getChildren(target).stream().filter(child -> child.getValue() != null).
                forEach(child -> child.getValue().visit(new ValueVisitor() {
                    @Override
                    public void visitLoop() {

                    }

                    @Override
                    public void visitInvalid(String exp) {

                    }

                    @Override
                    public void visitNumber(double val) {
                        valueMap.put(child.getLocation().toString(), val);
                    }

                    @Override
                    public void visitString(String exp) {
                        stringMap.put(child.getLocation().toString(), String.format("\"%s\"", exp));
                    }
                }));
        Parser parser = new Parser();
        parser.addValueMap(valueMap);
        parser.addStringMap(stringMap);
        parser.parse(target.getExpression());
        if (parser.isValid()) {
            return new vNumber(parser.getDouble());
        }
        return new vString(target.getExpression());
    }

    public void checkLoops(CellImpl target, Set<CellImpl> cellSeen) {
        if (cellSeen == null)
            cellSeen = new HashSet<>();

        if (cellSeen.contains(target)) {
            markAsLoop(target, cellSeen);
            return;
        }

        cellSeen.add(target);
        for (CellImpl child : getChildren(target)) {
            checkLoops(child, cellSeen);
        }

        cellSeen.remove(target);
    }

    private void markAsLoop(CellImpl target, Set<CellImpl> cellSet) {
        boolean loopConfirmed = false;

        for (CellImpl cell : cellSet) {
            cell.setModified(true);

            if (cell.getLocation().equals(target.getLocation()))
                loopConfirmed = true;
        }
        if (loopConfirmed)
            for (CellImpl cell : cellSet)
                cell.setValue(vLoop.INSTANCE);
    }

    public Set<CellImpl> getModified() {
        return modified;
    }

    public void clearAll() {
        locationCellMap.clear();
        modified.clear();
        ignore.clear();
    }
}
