package spreadsheet.gui.menu;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by zhaow on 12/28/2015.
 * SimpleExcel
 */
public class MenuItemFactory {
    ArrayList<JMenuItem> list;
    public MenuItemFactory(){
        list = new ArrayList<>();
    }

    public void addMenuItem(String text, String key, ActionListener action) {
        JMenuItem item = new JMenuItem();
        if (text != null)
            item.setText(text);
        if (action != null)
            item.addActionListener(action);
        if (key != null)
            item.setAccelerator(KeyStroke.getKeyStroke(key));
        list.add(item);
    }

    public ArrayList<JMenuItem> getList() {
        return list;
    }
}
