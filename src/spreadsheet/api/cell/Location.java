package spreadsheet.api.cell;

/**
 * Created by zhaow on 12/30/2015.
 * SimpleExcel
 */
public class Location {
    private String location;

    public Location(String location) {
        locationFactory(location);
    }

    public Location(int row, int column) {
        locationFactory(convertColumn(column) + (row + 1));
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

    private void locationFactory(String location) {
        if (location == null)
            throw new NullPointerException("Location Can't be null.");
        this.location = location;
    }

    public static String convertColumn(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("index can't be " + index);
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
    public static int maxLength(int input) {
        return (int) (Math.pow(26, input) - 1) * 26 / (26 - 1);
    }
}
