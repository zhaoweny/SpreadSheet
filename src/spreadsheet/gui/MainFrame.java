package spreadsheet.gui;

import spreadsheet.gui.menu.MenuBarFactory;
import spreadsheet.gui.menu.MenuFactory;
import spreadsheet.gui.menu.MenuItemFactory;
import spreadsheet.gui.table.RowNumberTable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by zhaow on 12/28/2015.
 * spreadsheet
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        MenuItemFactory itemFactory = new MenuItemFactory();
        MenuFactory menuFactory = new MenuFactory();
        itemFactory.addMenuItem("New", "ctrl N", null);
        itemFactory.addMenuItem("Save", "ctrl S", null);
        itemFactory.addMenuItem("Open", "ctrl O", null);
        itemFactory.addMenuItem("Exit", "alt F4", e -> System.exit(0));
        menuFactory.addMenu("File", itemFactory.getList());
        JMenuBar bar = MenuBarFactory.getMenuBar(menuFactory.getList());

        JTable table = new JTable(50,50);
        JScrollPane scrollPane = new  JScrollPane(table);
        JTable rowTable = new RowNumberTable(table);
        scrollPane.setRowHeaderView(rowTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                rowTable.getTableHeader());

        setLayout(new BorderLayout());

        add(bar, BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        validate();
        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

