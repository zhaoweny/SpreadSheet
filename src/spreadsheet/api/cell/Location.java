package spreadsheet.api.cell;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class Location {
    private String location;

    public Location(String location) {
        if (location == null)
            throw new NullPointerException("Location Can't be null.");
        this.location = location;
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return location.equals(other.location);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
