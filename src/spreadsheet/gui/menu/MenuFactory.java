package spreadsheet.gui.menu;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by zhaow on 12/29/2015.
 * SimpleExcel
 */
public class MenuFactory {
    ArrayList<JMenu> list = new ArrayList<>();

    public void addMenu(String name, ArrayList<JMenuItem> itemList){
        JMenu menu = new JMenu(name);
        itemList.forEach(menu::add);
        list.add(menu);
    }

    public ArrayList<JMenu> getList(){
        return list;
    }
}
