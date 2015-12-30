package spreadsheet.gui.menu;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by zhaow on 12/29/2015.
 * SimpleExcel
 */
public class MenuBarFactory {
    public static JMenuBar getMenuBar(ArrayList<JMenu> menus){
        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        return menuBar;
    }
}
