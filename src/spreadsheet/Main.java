package spreadsheet;

import spreadsheet.gui.MainFrame;
import spreadsheet.implement.SpreadsheetImpl;

/**
 * Created by zhaow on 12/28/2015.
 * spreadsheet
 */
public class Main {
    public static void main(String[] args) {
        SpreadsheetImpl spreadsheet = new SpreadsheetImpl();
        javax.swing.SwingUtilities.invokeLater(() -> new MainFrame(spreadsheet).setVisible(true));
    }
}
